package model;

import java.util.Map;
import java.util.Random;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a random walk cell.
 */
public class RandomWalkCell extends ActiveCell {
    private Position currentEndPosition;
    private Random random = new Random();

    /**
     * Create a cell at the given position
     *
     * @param position position of this node
     */
    public RandomWalkCell(Position position) {
        super(position);
        currentEndPosition = position;
    }

    @Override
    public void tick() {
        updatePosition();

        World.getInstance().add(new Cell(currentEndPosition));
    }

    private void updatePosition() {
        Position randomPosition = generateRandomPosition();

        this.currentEndPosition = currentEndPosition.add(randomPosition);
    }

    private Position generateRandomPosition() {
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
            case 5:
                return new Position(0, 0, -1);
            default:
                return null;
        }
    }
}
