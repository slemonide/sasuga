package daemons;

import cells.Cell;
import geometry.Position;
import world.World;

import java.util.Random;

/**
 * Random landscape animation
 */
public class RandomLandscape implements Runnable {
    private static final long DELAY = 50;
    private Random random = new Random();
    private Position currentPosition;

    public RandomLandscape() {
        currentPosition = new Position(0,0,0);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!Thread.interrupted()) {
            //World.getInstance().remove(currentPosition);
            World.getInstance().add(new Cell(currentPosition));
            currentPosition = currentPosition.add(nextPosition());
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private Position nextPosition() {
        switch (random.nextInt(6)) {
            case 0:
                return new Position(1, 0, 0);
            case 1:
                return new Position(-1, 0, 0);
            case 2:
                return new Position(1, 1, 0);
            case 3:
                return new Position(-1, 1, 0);
            case 4:
                return new Position(1, -1, 0);
            default:
                return new Position(-1, -1, 0);
        }
    }
}
