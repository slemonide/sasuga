package server.rulesets;

import server.model.Cell;
import server.model.Vector3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A 3D random walk simulation
 */

public class RandomWalk implements RuleSet {
    private Cell currentCell;
    private Set<Cell> cells;

    public RandomWalk() {
        cells = new HashSet<>();
        currentCell = new Cell(new Vector3(0, 0, 0));
    }

    @Override
    public void tick() {
        Vector3 randomVector = getRandomVector();
        Cell nextCell = new Cell(currentCell.getPosition().add(randomVector));

        cells.add(currentCell);
        currentCell = nextCell;
    }

    private Vector3 getRandomVector() {
        Random random = new Random();

        switch (random.nextInt(6)) {
            case 0:
                return new Vector3(1, 0, 0);
            case 1:
                return new Vector3(-1, 0, 0);
            case 2:
                return new Vector3(0, 1, 0);
            case 3:
                return new Vector3(0, 0, 0); // here
            case 4:
                return new Vector3(0, 0, 1);
            case 5:
                return new Vector3(0, 0, -1);
            default:
                return null;
        }
    }

    @Override
    public Set<Cell> getCells() {
        return cells;
    }

    @Override
    public void add(Cell cell) {
        // should not work
    }

    @Override
    public Set<Vector3> getNeighbourhood() {
        return null;
    }

    @Override
    public void setLowerBound(int i) {

    }

    @Override
    public void setUpperBound(int i) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void remove(Cell cell) {

    }

    @Override
    public void setNeighbourhood(Set<Vector3> neighbourhood) {

    }
}
