package server.model;

import java.util.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages world
 */
public class WorldManager extends Observable implements Runnable {
    private Set<Vector3> neighbourhood = new HashSet<>();
    private int upperCellBound = 3;
    private int lowerCellBound = 1;
    private static WorldManager instance;
    private Set<Cell> cells = new HashSet<Cell>();
    private long tickTime = 0;
    private int generation = 0;
    private int populationSize = 0;

    private WorldManager() {}

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

    public void setUpperBound(int limit) {
        upperCellBound = limit;
    }

    public void setLowerBound(int limit) {
        lowerCellBound = limit;
    }

    public int getUpperBound() {
        return upperCellBound;
    }

    public int getLowerBound() {
        return lowerCellBound;
    }

    public void setNeighbourhood(Set<Vector3> neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public Set<Vector3> getNeighbourhood() {
        return this.neighbourhood;
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
        populationSize++;
    };

    /**
     * Remove the given cell from the world
     * @param cell cell to be removed
     */
    public void remove(Cell cell) {
        cells.remove(cell);
        populationSize--;
    }

    /**
     * Remove all cells
     */
    public void clear() {
        cells.clear();
        populationSize = 0;
    }

    /**
     * Produce all of the cells as an unmodifiable list
     * <p>
     *     DO NOT USE for-each loop with this, use getCellsSnapshot() instead
     * </p>
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
        // tickTime measurement
        long startTime = System.nanoTime();

        Set<Cell> toAdd = grow();
        Set<Cell> toKill = die();

        cells.addAll(toAdd);
        cells.removeAll(toKill);

        // tickTime measurement
        long endTime = System.nanoTime();
        tickTime = endTime - startTime;

        // update generation
        generation++;

        // update populationSize
        populationSize += toAdd.size();
        populationSize -= toKill.size();

        setChanged();
        notifyObservers("tick");
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
            if (neighbourComplementElement.getNeighbours().size() == upperCellBound) {
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
            if (cell.getNeighbours().size() <= lowerCellBound || cell.getNeighbours().size() > upperCellBound) {
                toKill.add(cell);
            }
        }
        return toKill;
    }

    /**
     * Produce the time one tick takes
     * @return time in nano seconds
     */
    public long getTickTime() {
        return tickTime;
    }


    public int getPopulationSize() {
        return populationSize;
    }

    /**
     * Produce the current generation
     * @return generation
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Resets current world state
     */
    public void reset() {
        instance = new WorldManager();
    }
}
