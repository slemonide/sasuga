package model;

import java.util.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages world
 */
public class World extends Observable implements Runnable {
    private long tickTime;
    private int generation;
    private Map<Position, Cell> cellsMap;
    private int population;
    private Thread worldThread;
    private static World instance;

    /**
     * Create a new empty world
     */
    private World() {
        tickTime = 0;
        generation = 0;
        population = 0;
        cellsMap = new HashMap<>();
        worldThread = new Thread(this);
    }

    /**
     * Singleton pattern
     * @return the current active instance of the world
     */
    public static World getInstance() {
        if (instance == null){
            instance = new World();
        }
        return instance;
    }

    /**
     * Start time by consequently running tick()'s on all the active cells
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            tick();
            generation++;
        }
    }

    /**
     * Produce the time one tick takes
     * @return time in nano seconds
     */
    public long getTickTime() {
        return tickTime;
    }

    /**
     * Produce the current generation
     * @return generation
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Produce the current population size
     * @return population
     */
    public int getPopulationSize() {
        return population;
    }

    /**
     * @return Unmodifiable collection of all the cells in the world
     */
    public Collection<Cell> getCells() {
        return Collections.unmodifiableCollection(cellsMap.values());
    }

    /**
     * Adds the given cell to the world, updates population and generation
     * and notify the observers about the change
     * @param cell cell to add
     */
    public void add(Cell cell) {
        cellsMap.put(cell.getPosition(), cell);
        population++;
        generation++;

        setChanged();
        notifyObservers();
    }

    /**
     * Removes the given cell from the world and updates population and generation
     * and notify the observers about the change
     * @param cell cell to remove
     */
    public void remove(Cell cell) {
        cellsMap.remove(cell.getPosition());
        population--;
        generation++;

        setChanged();
        notifyObservers();
    }

    public void remove(Position currentSelection) {
        cellsMap.remove(currentSelection);
        population--;
        generation++;

        setChanged();
        notifyObservers();
    }

    /**
     * Update all active cells, update the tick time and notify the observers about the change
     */
    public void tick() {
        // tickTime measurement
        long startTime = System.nanoTime();

        Set<Cell> oldCells = getOldCells();

        for (Cell cell : oldCells) {
            if (cell instanceof ActiveCell) {
                ActiveCell currentCell = (ActiveCell) cell;
                currentCell.tick();
            }
        }
        generation++;

        // tickTime measurement
        long endTime = System.nanoTime();
        tickTime = endTime - startTime;

        setChanged();
        notifyObservers();
    }

    /**
     * Freezes old version of the world
     * @return frozen old version of the world
     */
    public Set<Cell> getOldCells() {
        Set<Cell> oldCells = new HashSet<>();
        oldCells.addAll(cellsMap.values());

        return Collections.synchronizedSet(oldCells);
    }

    /**
     * Reset the world and notify the observers about the change
     */
    public void reset() {
        instance = new World();

        setChanged();
        notifyObservers();
    }

    /**
     * Starts the world thread
     */
    public void start() {
        worldThread.start();
    }

    /**
     * Interrupts the world thread
     */
    public void interrupt() {
        worldThread.interrupt();
    }
}
