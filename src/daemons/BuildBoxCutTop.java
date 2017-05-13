package daemons;

import cells.Cell;
import geometry.Position;
import world.World;

/**
 * An animation that builds a cube
 */
public class BuildBoxCutTop implements Runnable {

    private static final long DELAY = 100; // in ms
    private static final int SIZE_X = 10;
    private static final int SIZE_Y = 20;
    private static final int SIZE_Z = 17;

    @Override
    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int x=0; x < SIZE_X; x++) {
            for (int y=0; y < SIZE_Y; y++) {
                for (int z=0; z < SIZE_Z; z++) {
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    World.getInstance().add(new Cell(new Position(x,y,z)));
                }
            }
        }

        for (int x=0; x < SIZE_X; x++) {
            for (int y=0; y < SIZE_Y; y++) {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                World.getInstance().remove(new Position(x,y,SIZE_Z - 1));
            }
        }
    }
}
