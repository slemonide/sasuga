package server.application;

import java.util.Collections;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

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
    private void tick() {
        grow();
        die();
    }

    private void grow() {
        for (Cell cell : cells) {
            growAround(cell);
        }
    }

    private void growAround(Cell cell) {
        for (Cell neighbourComplementElement : cell.getNeighboursComplement()) {
            tryGrowing(neighbourComplementElement);
        }
    }

    private void tryGrowing(Cell cell) {
        if (cell.getNeighbours().size() == 3) {
            cells.add(cell);
        }
    }

    private void die() {
        for (Cell cell : cells) {
            tryKilling(cell);
        }
    }

    private void tryKilling(Cell cell) {
        if (cell.getNeighbours().size() <= 1 || cell.getNeighbours().size() > 3) {
            cells.remove(cell);
        }
    }
}
