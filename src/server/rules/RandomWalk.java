package server.rules;

import server.model.Cell;
import server.model.Position;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>, jacketsj <jacketsj@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A 3D random walk simulation
 */

public class RandomWalk implements ActiveRule {
    private static final int NUMBER_OF_DIMENSIONS = 20;
    private Set<Cell> currentCells;
    private Set<Cell> cells;

    private Random random;

    private final int DIM = 12;

    public RandomWalk() {
        random = new Random();

        cells = new HashSet<>();
        currentCells = new HashSet<>();

        int no = NUMBER_OF_DIMENSIONS  / 10;
        int[][] pos = new int[no][];
        for (int i = 0; i < no; ++i) {
            pos[i] = new int[DIM];
            for (int j = 0; j < DIM; j++) {
                pos[i][j] = 0;
                if (j % 3 == 0) {
                    pos[i][j] = i * 10 + j * 2;
                }
            }
            currentCells.add(new Cell(new Position(pos[i])));
        }
    }

    @Override
    public void tick() {
        Set<Cell> nextCells = new HashSet<>();

        for (Cell cell : currentCells) {
            cells.add(cell);

            Cell nextCell = new Cell(cell.getPosition().add(getRandomVector()));

            nextCells.add(nextCell);
        }

        currentCells = nextCells;
    }

    private int[] Zero() {
        int[] ret = new int[DIM];
        for (int i = 0; i < DIM; ++i) {
            ret[i] = 0;
        }
        return ret;
    }

    private Position getRandomVector() {
        int ran = random.nextInt(DIM * 2-1);
        Position newV = new Position(Zero());
        int dif = 1;
        if (ran % 2 == 0) {
            dif = -1;
        }
        if (ran != 0) { // go "up" only on e_1 axis
            // TODO: vector is immutable
            //newV.getComponent(ran / 2) = dif;
        }

        return newV;
    }

    @Override
    public Set<Cell> getCells() {
        return cells;
    }

    @Override
    public Set<Cell> getToAdd() {
        return currentCells;
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
    public Set<Position> getNeighbourhood() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void remove(Cell cell) {

    }
}
