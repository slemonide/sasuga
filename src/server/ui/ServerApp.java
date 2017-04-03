package server.ui;

import exceptions.InvalidDensityException;
import exceptions.InvalidDimensionException;
import server.model.WorldGenerator;
import server.model.WorldManager;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Main app
 */
public class ServerApp {
    public static void main(String[] args) {
        System.out.print("Starting server... ");
        Thread worldThread = new Thread(WorldManager.getInstance());

        // Generate
        try {
            WorldGenerator.generate(0.1, 50, 50, 50);
        } catch (InvalidDensityException e) {
            e.printStackTrace();
        } catch (InvalidDimensionException e) {
            e.printStackTrace();
        }

        worldThread.start();
        System.out.println("OK");

        WorldManager.getInstance().addObserver(ConsoleUI.getInstance());
    }
}
