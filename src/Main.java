import animations.BuildCube;
import animations.RandomLandscape;
import model.*;
import ui.ConsoleUI;
import ui.HUDController;
import ui.VisualGUI;

/**
 * Starts the application
 */
public class Main {
    private static final int SIZE = 10;

    public static void main(String[] args) {
        //World.getInstance().addObserver(ConsoleUI.getInstance());
        //World.getInstance().add(new RandomWalkCell(new Position()));
        /*for (int x=0; x < SIZE; x++) {
            for (int y=0; y < SIZE; y++) {
                for (int z=0; z < SIZE; z++) {
                    World.getInstance().add(new Cell(new Position(x,y,z)));
                }
            }
        }*/

        Thread buildCubeAnimation = new Thread(new BuildCube());
        buildCubeAnimation.start();

        //Thread randomLandscape = new Thread(new RandomLandscape());
        //randomLandscape.start();

        World.getInstance().start();
        VisualGUI.main(args);
    }
}
