package server.application;

import java.util.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages world
 */
public class WorldManager extends Observable implements Runnable {
    private static WorldManager instance;
    private Set<Cell> cells;

    /**
     * Creates a new world with no cells in it
     */
    private WorldManager() {
        cells = new HashSet<Cell>();
    }

    /**
     * Singleton pattern
     * @return instance
     */
    public static WorldManager getInstance() {
        if(instance == null){
            instance = new WorldManager();
        }
        return instance;
    }

    /**
     * Start time
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            tick();
        }
    }

    /**
     * Adds the given cell to the world
     * @param cell cell to be added
     */
    public void add(Cell cell) {
        cells.add(cell);
    };

    /**
     * Remove the given cell from the world
     * @param cell cell to be removed
     */
    public void remove(Cell cell) {
        cells.remove(cell);
    }

    /**
     * Remove all cells
     */
    public void clear() {
        cells.clear();
    }

    /**
     * Produce all of the cells as an unmodifiable list
     * @return all cells in the world
     */
    public Set<Cell> getCells() {
        return Collections.unmodifiableSet(cells);
    }

    /**
     * Updates the world according to the set rules:
     * If there are 3 cells around a given black space, new cell emerges.
     * If there are 2 or 3 cells around a given cell, cell survives.
     * If there are 0, 1 or more than 3 cells around a given cell, cell dies off
     */
    public void tick() {
        Set<Cell> toAdd = grow();
        Set<Cell> toKill = die();

        cells.addAll(toAdd);
        cells.removeAll(toKill);
    }

    /**
     * @return set of the cells to be added
     */
    private Set<Cell> grow() {
        Set<Cell> newCells = new HashSet<Cell>();

        for (Cell cell : cells) {
            newCells.addAll(growAround(cell));
        }

        return newCells;
    }

    /**
     * @param cell cell around which to compute next cells to be added
     * @return cells to be added
     */
    private Set<Cell> growAround(Cell cell) {
        Set<Cell> newCells = new HashSet<Cell>();

        Set<Cell> neighboursComplement = cell.getNeighboursComplement();
        for (Cell neighbourComplementElement : neighboursComplement) {
            if (neighbourComplementElement.getNeighbours().size() == 3) {
                newCells.add(neighbourComplementElement);
            }
        }

        return newCells;
    }

    /**
     * @return a set of cells to be removed from the world
     */
    private Set<Cell> die() {
        Set<Cell> toKill = new HashSet<Cell>();
        for (Cell cell : cells) {
            if (cell.getNeighbours().size() <= 1 || cell.getNeighbours().size() > 3) {
                toKill.add(cell);
            }
        }
        return toKill;
    }
}
