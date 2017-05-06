package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a random walk cell.
 */
public class RandomWalkCell extends ActiveCell {
    private static Random random = new Random();

    /**
     * Create a cell at the given position
     *
     * @param position position of this node
     */
    public RandomWalkCell(Position position) {
        super(position);
        setName("Random Walk Cell");
    }

    @Override
    public void tick() {
        // do nothing
    }

    @Override
    public Collection<? extends Cell> tickToAdd() {
        Set<Cell> nextCells = new HashSet<>();
        nextCells.add(new Cell(position));
        nextCells.add(new RandomWalkCell(position.add(nextPosition())));

        return nextCells;
    }

    @Override
    public Collection<? extends Position> tickToRemove() {
        return null;
    }

    public static Position nextPosition() {
        switch (random.nextInt(6)) {
            case 0:
                return new Position(1, 0, 0);
            case 1:
                return new Position(-1, 0, 0);
            case 2:
                return new Position(0, 1, 0);
            case 3:
                return new Position(0, -1, 0);
            case 4:
                return new Position(0, 0, 1);
            default:
                return new Position(0, 0, -1);
        }
    }
}
