package ui;

import model.World;

import java.util.Observable;
import java.util.Observer;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A simple console logging UI
 */
public class ConsoleUI implements Observer {
    private static ConsoleUI instance;

    private ConsoleUI() {};

    /**
     * Singleton pattern
     * @return instance
     */
    public static ConsoleUI getInstance() {
        if(instance == null){
            instance = new ConsoleUI();
        }
        return instance;
    }

    public void update(Observable o, Object arg) {
        System.out.print("Generation: " + World.getInstance().getGeneration());
        System.out.print("    ");
        System.out.print("Number of cells: " + World.getInstance().getPopulationSize());
        System.out.print("    ");
        System.out.print("Tick time: " + (double) World.getInstance().getTickTime() / 1000000000 + " s");
        System.out.print("\n");
    }
}