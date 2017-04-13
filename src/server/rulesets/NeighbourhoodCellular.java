package server.rulesets;

import server.model.Cell;
import server.model.Vector3;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a cellular automata with configurable neighbourhood
 */
public class NeighbourhoodCellular implements RuleSet {
    private Set<Vector3> neighbourhood;
    private int upperCellBound;
    private int lowerCellBound;
    Set<Cell> cells;
    Set<Cell> toAdd;
    Set<Cell> toRemove;
    int populationSize;

    public NeighbourhoodCellular() {
        neighbourhood = new HashSet<>();
        cells = new HashSet<Cell>();
        upperCellBound = 3;
        lowerCellBound = 1;
        populationSize = 0;

        toAdd = new HashSet<>();
        toRemove = new HashSet<>();
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
     * Adds the given cell to the world
     * @param cell cell to be added
     */
    public void add(Cell cell) {
        cells.add(cell);
        populationSize++;
    }

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

    @Override
    public Set<Cell> getToAdd() {
        return toAdd;
    }

    @Override
    public Set<Cell> getToRemove() {
        return toRemove;
    }

    /**
     * Updates the world according to the set rules:
     * If there are 3 cells around a given black space, new cell emerges.
     * If there are 2 or 3 cells around a given cell, cell survives.
     * If there are 0, 1 or more than 3 cells around a given cell, cell dies off
     */
    public void tick() {
        toAdd = grow(cells);
        toRemove = die(cells);

        cells.addAll(toAdd);
        cells.removeAll(toRemove);

        // update populationSize
        populationSize += toAdd.size();
        populationSize -= toRemove.size();
    }

    /**
     * @return set of the cells to be added
     * @param cells
     */
    Set<Cell> grow(Set<Cell> cells) {
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
     * @param cells
     */
    Set<Cell> die(Set<Cell> cells) {
        Set<Cell> toKill = new HashSet<Cell>();
        for (Cell cell : cells) {
            if (cell.getNeighbours().size() <= lowerCellBound || cell.getNeighbours().size() > upperCellBound) {
                toKill.add(cell);
            }
        }
        return toKill;
    }

    public int getPopulationSize() {
        return populationSize;
    }
}
