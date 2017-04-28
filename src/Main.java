import model.Player;
import model.Position;
import model.RandomWalkCell;
import model.World;
import ui.ConsoleUI;
import ui.HUDController;
import ui.VisualGUI;

/**
 * Starts the application
 */
public class Main {
    public static void main(String[] args) {
        //World.getInstance().addObserver(ConsoleUI.getInstance());
        //World.getInstance().add(new RandomWalkCell(new Position()));
        World.getInstance().start();
        VisualGUI.main(args);
    }
}
