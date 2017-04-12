package server.model;

import server.rulesets.RuleSet;

import java.awt.*;
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
    private RuleSet rule;
    private long tickTime = 0;
    private int generation = 0;

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

    public void setRule(RuleSet rule) {
        this.rule = rule;
    }

    public RuleSet getRule() {
        return rule;
    }

    /**
     * Start time
     * If rule is not set, don't do anything
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // tickTime measurement
            long startTime = System.nanoTime();

            if (rule != null) {
                rule.tick();
            }

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
        rule.clear();
    }

    public Set<Cell> getCells() {
        return rule.getCells();
    }

    public void add(Cell cell) {
        rule.add(cell);
    }

    public void remove(Cell cell) {
        rule.remove(cell);
    }

    public int getPopulationSize() {
        return rule.getCells().size();
    }

    public void tick() {
        rule.tick();
    }
}
