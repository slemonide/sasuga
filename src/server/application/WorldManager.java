package server.application;

import java.util.*;

/**
 * Manages chunks
 */
public class WorldManager extends Observable implements Runnable {
    private static WorldManager instance;
    private Set<Cell> cells;

    private WorldManager() {
        cells = new HashSet<Cell>();
    }

    /**
     * Singleton pattern
     * @return
     */
    public static WorldManager getInstance() {
        if(instance == null){
            instance = new WorldManager();
        }
        return instance;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            tick();
        }
    }

    public void add(Cell cell) {
        cells.add(cell);
    };

    public void remove(Cell cell) {
        cells.remove(cell);
    }

    public void clear() {
        cells.clear();
    }

    /**
     * Produce all of the cells as an unmodifiable list
     * @return
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

    private Set<Cell> grow() {
        Set<Cell> newCells = new HashSet<Cell>();

        for (Cell cell : cells) {
            newCells.addAll(growAround(cell));
        }

        return newCells;
    }

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
