import model.ClientManager;
import model.World;
import ui.ConsoleUI;

/**
 * Starts the server
 */
public class Main {
    public static void main(String[] args) {
        System.out.print("Starting server... ");
        initializeObservers();
        initializeThreads();
        System.out.println("OK");
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
