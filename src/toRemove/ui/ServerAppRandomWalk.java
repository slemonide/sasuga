package toRemove.ui;

import server.model.World;
import server.ui.ConsoleUI;

/**
 * @author      Danil Platonov <slemonide@gmail.com>, jacketsj <jacketsj@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Main app
 */
public class ServerAppRandomWalk {
    public static void main(String[] args) {
        System.out.print("Starting server... ");
        //World.getInstance().setRule(new RandomWalk());

        Thread worldThread = new Thread(World.getInstance());

        //worldThread.start();
        System.out.println("OK");

        World.getInstance().addObserver(ConsoleUI.getInstance());
        World.getInstance().addObserver(PopulationGraphGUI.getInstance());
        //World.getInstance().addObserver(VisualGUI.getInstance());

        // Launch all windows
        //Application.launch(PopulationGraphGUI.class, args);
        VisualGUI.main(args);
    }
}
