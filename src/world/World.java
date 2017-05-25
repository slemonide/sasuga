package world;

import cells.WorldCell;
import geometry.Parallelepiped;
import geometry.Position;
import org.jetbrains.annotations.NotNull;
import util.CollectionObserver;
import util.Difference;

import java.util.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages world
 */
public class World extends Observable implements Runnable {
    private static final double TICK_DELAY = 0.01; // in seconds
    public static final int TICKS_PER_SECOND = (int) (1.0 / TICK_DELAY);
    private long tickTime;
    private int generation;
    private int tickDelayNumber;
    private CellStorage cellStorage;
    private int population;
    private Thread worldThread;
    private static World instance;
    private int growthRate;
    private Set<CellParallelepiped> toAdd;
    private Set<Position> toRemove;
    private Set<CellParallelepiped> lastSeenCellsSetToAdd;
    private Set<Position> lastSeenPositionSetToRemove;

    /**
     * Create a new empty world
     */
    private World() {
        tickTime = 0;
        generation = 0;
        population = 0;
        tickDelayNumber = 0;
        cellStorage = new HashMapCellStorage();
        worldThread = new Thread(this);

        lastSeenCellsSetToAdd = new HashSet<>();
        lastSeenPositionSetToRemove = new HashSet<>();

        toAdd = new HashSet<>();
        toRemove = new HashSet<>();
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
        return (double) tickTime / (1000 * 1000 * 1000);
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
    public Collection<CellParallelepiped> getCells() {
        return Collections.unmodifiableCollection(cellStorage.cellParallelepipeds());
    }

    /**
     * Adds the given cellParallelepiped to the world, updates population and generation
     * and notify the observers about the change
     * @param cellParallelepiped cellParallelepiped to add
     */
    public void add(CellParallelepiped cellParallelepiped) {
        toAdd.add(cellParallelepiped);
    }

    /**
     * Removes the given cellParallelepiped from the world and updates population and generation
     * and notify the observers about the change
     * @param cellParallelepiped cellParallelepiped to remove
     */
    void remove(CellParallelepiped cellParallelepiped) {
        toRemove.add(cellParallelepiped.parallelepiped.getCorner());
    }

    public void remove(Position currentSelection) {
        CellParallelepiped cellParallelepipedToRemove = null;
        for (CellParallelepiped cellParallelepiped : toAdd) {
            if (cellParallelepiped.parallelepiped.getCorner().equals(currentSelection)) {
                cellParallelepipedToRemove = cellParallelepiped;
                break;
            }
        }

        if (cellParallelepipedToRemove != null) {
            toAdd.remove(cellParallelepipedToRemove);
        }

        toRemove.add(currentSelection);
    }

    /**
     * Update all active cells, update the tick time and notify the observers about the change
     */
    public void tick() {
        // tickTime measurement
        long startTime = System.nanoTime();

        /*
        for (ActiveCell cell : cellStorage.activeCellsValues()) {
            if (cell.delay == 0 || tickDelayNumber % cell.delay == 0) {
                Collection<? extends CellParallelepiped> toAddFromThisCell = cell.tickToAdd();
                Collection<? extends Position> toRemoveFromThisCell = cell.tickToRemove();

                if (toAddFromThisCell != null) {
                    toAdd.addAll(toAddFromThisCell);
                }
                if (toRemoveFromThisCell != null) {
                    toRemove.addAll(toRemoveFromThisCell);
                }

                cell.tick();
            }
        }
        tickDelayNumber++;*/

        // update cursor location
        // NOTE: if too costly, move to GUI and use only on camera movement
       // cursor.tick();

        // update growth rate
        growthRate = toAdd.size() - toRemove.size();
        // if there's no change, don't update the generation counter
        if (!(toAdd.isEmpty() && toRemove.isEmpty())) {
            cellStorage.removeAll(toRemove);
            cellStorage.addAll(toAdd);
            population += growthRate;
            generation++;

            setChanged();
            notifyObservers();

            // tickTime measurement
            long endTime = System.nanoTime();
            tickTime = endTime - startTime;

            // reset toAdd and toRemove
            toAdd.clear();
            toRemove.clear();
        }
    }

    /**
     * Reset the world and notify the observers about the change
     */
    void reset() {
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

    CellStorage getCellStorage() {
        return cellStorage;
    }

    /**
     * @return set of cells removed since last invocation of getToRemove()
     */
    Set<CellParallelepiped> getToAdd() {
        Set<CellParallelepiped> toAdd = new HashSet<>();
        toAdd.addAll(getCells());
        toAdd.removeAll(lastSeenCellsSetToAdd);

        lastSeenCellsSetToAdd.clear();
        lastSeenCellsSetToAdd.addAll(getCells());

        return toAdd;
    }

    /**
     * @return set of cells added since last invocation of getToAdd()
     */
    Set<Position> getToRemove() {
        Set<Position> toRemove = new HashSet<>();
        toRemove.addAll(lastSeenPositionSetToRemove);
        toRemove.removeAll(cellStorage.positions());
        lastSeenPositionSetToRemove.clear();
        lastSeenPositionSetToRemove.addAll(cellStorage.positions());

        return toRemove;
    }

    public boolean containsCellAt(Position position) {
        return cellStorage.contains(position);
    }

    public void add(Position placeCursor, WorldCell staticCell) {
        add(new CellParallelepiped(new Parallelepiped(placeCursor), staticCell));
    }

    /**
     * Helps analyzing changes in the world
     * @return difference between current world and its last seen version
     */
    @NotNull
    public CollectionObserver<CellParallelepiped> registerCollectionObserver() {
        return cellStorage.registerCollectionObserver();
    }
}
