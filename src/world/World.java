package world;

import cells.ActiveCell;
import cells.Cell;
import geometry.Position;

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
    private CellsMap cellsMap;
    private int population;
    private Thread worldThread;
    private static World instance;
    private int growthRate;
    private Set<Cell> toAdd;
    private Set<Position> toRemove;
    private Set<Cell> lastSeenCellsSetToAdd;
    private Set<Position> lastSeenPositionSetToRemove;

    /**
     * Create a new empty world
     */
    private World() {
        tickTime = 0;
        generation = 0;
        population = 0;
        tickDelayNumber = 0;
        cellsMap = new CellsMap();
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
        toAdd.add(cell);
    }

    /**
     * Removes the given cell from the world and updates population and generation
     * and notify the observers about the change
     * @param cell cell to remove
     */
    void remove(Cell cell) {
        toRemove.add(cell.getPosition());
    }

    public void remove(Position currentSelection) {
        Cell cellToRemove = null;
        for (Cell cell : toAdd) {
            if (cell.position.equals(currentSelection)) {
                cellToRemove = cell;
                break;
            }
        }

        if (cellToRemove != null) {
            toAdd.remove(cellToRemove);
        }

        toRemove.add(currentSelection);
    }

    /**
     * Update all active cells, update the tick time and notify the observers about the change
     */
    public void tick() {
        // tickTime measurement
        long startTime = System.nanoTime();

        for (ActiveCell cell : cellsMap.activeCellsValues()) {
            if (cell.delay == 0 || tickDelayNumber % cell.delay == 0) {
                Collection<? extends Cell> toAddFromThisCell = cell.tickToAdd();
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
        tickDelayNumber++;

        // update cursor location
        // NOTE: if too costly, move to GUI and use only on camera movement
       // cursor.tick();

        // update growth rate
        growthRate = toAdd.size() - toRemove.size();
        // if there's no change, don't update the generation counter
        if (!(toAdd.isEmpty() && toRemove.isEmpty())) {
            cellsMap.removeAll(toRemove);
            cellsMap.addAll(toAdd);
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

    public CellsMap getCellsMap() {
        return cellsMap;
    }

    /**
     * @return set of cells removed since last invocation of getToRemove()
     */
    public Set<Cell> getToAdd() {
        Set<Cell> toAdd = new HashSet<>();
        toAdd.addAll(getCells());
        toAdd.removeAll(lastSeenCellsSetToAdd);

        lastSeenCellsSetToAdd.clear();
        lastSeenCellsSetToAdd.addAll(getCells());

        return toAdd;
    }

    /**
     * @return set of cells added since last invocation of getToAdd()
     */
    public Set<Position> getToRemove() {
        Set<Position> toRemove = new HashSet<>();
        toRemove.addAll(lastSeenPositionSetToRemove);
        toRemove.removeAll(cellsMap.keySet());
        lastSeenPositionSetToRemove.clear();
        lastSeenPositionSetToRemove.addAll(cellsMap.keySet());

        return toRemove;
    }

    public boolean containsCellAt(Position position) {
        return cellsMap.containsKey(position);
    }
}
