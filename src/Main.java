import model.Player;
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
        World.getInstance().add(Player.getInstance());
        World.getInstance().start();
        VisualGUI.main(args);
    }
}
