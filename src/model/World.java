package model;

import ui.HUDController;

import java.util.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages world
 */
public class World extends Observable implements Runnable {
    private static final double TICK_DELAY = 0.001; // in seconds
    static final int TICKS_PER_SECOND = (int) (1.0 / TICK_DELAY);
    private long tickTime;
    private int generation;
    private CellsMap cellsMap;
    private int population;
    private Thread worldThread;
    private static World instance;
    private int growthRate;

    /**
     * Create a new empty world
     */
    private World() {
        tickTime = 0;
        generation = 0;
        population = 0;
        cellsMap = new CellsMap();
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
    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        while (!Thread.currentThread().isInterrupted()) {
            if ((System.currentTimeMillis() - lastTime) > TICK_DELAY * 1000) {
                tick();
                lastTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Produce the time one tick takes
     * @return time in seconds
     */
    public double getTickTime() {
        return (double) tickTime / 1000000000;
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
        if (cellsMap.containsKey(cell.getPosition())) {
            return; // can't replace what's placed
        }

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

        // update growth rate
        growthRate = cellsMap.size() - oldCells.size();

        setChanged();
        notifyObservers();
    }

    /**
     * Freezes old version of the world
     * @return frozen old version of the world
     */
    public Set<Cell> getOldCells() {
        Set<Cell> oldCells = new HashSet<>();

        Set s = cellsMap.keySet();

        synchronized (cellsMap) {
            for (Object value : s) oldCells.add(cellsMap.get(value));
        }

        return oldCells;
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

    public int getGrowthRate() {
        return growthRate;
    }
}
