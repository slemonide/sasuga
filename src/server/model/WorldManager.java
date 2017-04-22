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
    private static WorldManager instance;
    private long tickTime = 0;
    private int generation = 0;
    private Map<Position, Cell> cellsMap;

    private WorldManager() {
        cellsMap = new HashMap<>();
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
     * If rule is not set, don't do anything
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // tickTime measurement
            long startTime = System.nanoTime();
            tick();

            // tickTime measurement
            long endTime = System.nanoTime();
            tickTime = endTime - startTime;

            // update generation
            generation++;

            setChanged();
            notifyObservers("tick");
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
     * Resets current world state
     */
    public void reset() {
        instance = new WorldManager();
    }

    public void clear() {
        cellsMap.clear();
    }

    public Set<Cell> getCells() {
        return null; // stub
    }

    public void add(Cell cell) {
        cellsMap.put(cell.getPosition(), cell);
    }

    public void remove(Cell cell) {
        cellsMap.remove(cell.getPosition());
    }

    public int getPopulationSize() {
        return cellsMap.size();
    }

    public void tick() {
        for (Cell cell : cellsMap.values()) {
            if (cell instanceof ActiveCell) {
                ActiveCell currentCell = (ActiveCell) cell;
                currentCell.tick();
            }
        }
        generation++;
    }
}
