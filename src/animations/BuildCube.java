package animations;

import model.Cell;
import model.Position;
import model.World;

/**
 * An animation that builds a cube
 */
public class BuildCube implements Runnable {

    private static final int SIZE = 10;
    private static final long DELAY = 50; // in ms

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int x=0; x < SIZE; x++) {
            for (int y=0; y < SIZE; y++) {
                for (int z=0; z < SIZE; z++) {
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    World.getInstance().add(new Cell(new Position(x,y,z)));
                }
            }
        }
    }
}
