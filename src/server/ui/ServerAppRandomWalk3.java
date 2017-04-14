package server.ui;

import server.model.WorldManager;
import server.rulesets.RandomWalk3;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Main app
 */
public class ServerAppRandomWalk3 {
    public static void main(String[] args) {
        System.out.print("Starting server... ");
        WorldManager.getInstance().setRule(new RandomWalk3());

        Thread worldThread = new Thread(WorldManager.getInstance());

        //worldThread.start();
        System.out.println("OK");

        WorldManager.getInstance().addObserver(ConsoleUI.getInstance());
        WorldManager.getInstance().addObserver(PopulationGraphGUI.getInstance());
        //WorldManager.getInstance().addObserver(VisualGUI.getInstance());

        // Launch all windows
        //Application.launch(PopulationGraphGUI.class, args);
        VisualGUI.main(args);
    }
}
