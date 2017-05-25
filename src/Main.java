import geometry.Position;
import world.*;
import ui.VisualGUI;

import static cells.StaticCell.WOOD;

/**
 * Starts the application
 */
public class Main {
    private static final int SIZE = 10;

    public static void main(String[] args) {
        //World.getInstance().addObserver(ConsoleUI.getInstance());
        //World.getInstance().add(new RandomWalkCell(new Position()));
        for (int x=0; x < SIZE; x++) {
            for (int y=0; y < SIZE; y++) {
                for (int z=0; z < SIZE; z++) {
                    World.getInstance().add(new CellParallelepiped(new Position(x,y,z), WOOD));
                }
            }
        }

        //Thread buildCubeAnimation = new Thread(new BuildCube());
        //buildCubeAnimation.start();

        //Thread randomLandscape = new Thread(new RandomLandscape());
        //randomLandscape.start();

        //Thread buildBoxAnimation = new Thread(new BuildBoxCutTop());
        //  buildBoxAnimation.start();

        World.getInstance().start();
        VisualGUI.main(args);
    }
}
