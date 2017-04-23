import model.World;
import ui.VisualGUI;

/**
 * Starts the application
 */
public class Main {
    public static void main(String[] args) {
        //World.getInstance().addObserver(ConsoleUI.getInstance());
        World.getInstance().start();
        VisualGUI.main(args);
    }
}
