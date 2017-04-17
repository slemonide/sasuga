package server.rulesets;

import server.model.Cell;
import server.model.Vector;

import java.util.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A 3D random walk simulation
 */

public class RandomWalk3 implements Rule {
    private List<Cell> currentCells; // two current cells can be at the same position
    private Set<Cell> cells;

    public RandomWalk3() {
        cells = new HashSet<>();
        currentCells = new ArrayList<>();
        currentCells.add(new Cell(new Vector(0, 0, 0)));
        currentCells.add(new Cell(new Vector(0, 0, 0)));
        currentCells.add(new Cell(new Vector(0, 0, 0)));
        currentCells.add(new Cell(new Vector(0, 0, 0)));
        currentCells.add(new Cell(new Vector(0, 0, 0)));
        currentCells.add(new Cell(new Vector(0, 0, 0)));
    }

    @Override
    public void tick() {
        List<Cell> nextCells = new ArrayList<>();

        for (Cell cell : currentCells) {
            cells.add(cell);

            Cell nextCell = new Cell(cell.getPosition().add(getRandomVector()));

            nextCells.add(nextCell);
        }

        currentCells = nextCells;
    }

    private Vector getRandomVector() {
        Random random = new Random();

        switch (random.nextInt(6)) {
            case 0:
                return new Vector(1, 0, 0);
            case 1:
                return new Vector(-1, 0, 0);
            case 2:
                return new Vector(0, 1, 0);
            case 3:
                return new Vector(0, -1, 0);
            case 4:
                return new Vector(0, 0, 1);
            case 5:
                return new Vector(0, 0, -1);
            default:
                return null;
        }
    }

    @Override
    public Set<Cell> getCells() {
        return cells;
    }

    @Override
    public Set<Cell> getToAdd() {
        return (Set<Cell>) currentCells;
    }

    @Override
    public Set<Cell> getToRemove() {
        // nothing to remove
        return new HashSet<>();
    }

    @Override
    public void add(Cell cell) {
        // should not work
    }

    @Override
    public Set<Vector> getNeighbourhood() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void remove(Cell cell) {

    }
}
