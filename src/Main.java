import model.ClientManager;
import model.World;
import ui.ConsoleUI;
import ui.VisualGUI;

/**
 * Starts the application
 */
public class Main {
    public static void main(String[] args) {
        initializeObservers();
        initializeThreads();
        startGUI(args);
    }

    private static void startGUI(String[] args) {
        VisualGUI.main(args);
    }

    private static void initializeThreads() {
        Thread worldThread = new Thread(World.getInstance());
        worldThread.start();
        Thread clientManagerThread = new Thread(ClientManager.getInstance());
        clientManagerThread.start();
    }

    private static void initializeObservers() {
        World.getInstance().addObserver(ConsoleUI.getInstance());
    }
}
