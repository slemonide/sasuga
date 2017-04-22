package toRemove.ui;

import server.model.World;
import server.ui.ConsoleUI;

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
        //World.getInstance().setRule(new RandomWalk3());

        Thread worldThread = new Thread(World.getInstance());
        World.getInstance().addObserver(ConsoleUI.getInstance());

        System.out.println("OK");

        System.out.print("Generating...");
        System.out.println("OK");

        World.getInstance().addObserver(PopulationGraphGUI.getInstance());
        //World.getInstance().addObserver(VisualGUI.getInstance());

        // Launch all windows
        //Application.launch(PopulationGraphGUI.class, args);
        VisualGUI.main(args);
    }
}
