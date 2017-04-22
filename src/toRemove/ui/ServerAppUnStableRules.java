package toRemove.ui;

import javafx.application.Application;
import server.exceptions.InvalidDensityException;
import server.exceptions.InvalidDimensionException;
import server.model.Position;
import server.model.WorldGenerator;
import server.model.WorldManager;
import server.rulesets.NeighbourhoodCellular;
import server.ui.ConsoleUI;

import java.util.HashSet;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Main app
 */
public class ServerAppUnStableRules {
    public static void main(String[] args) {
        System.out.print("Starting server... ");

        NeighbourhoodCellular gameOfLife = new NeighbourhoodCellular();

        gameOfLife.setLowerBound(1);
        gameOfLife.setUpperBound(2);

        Set<Position> neighbourhood = new HashSet<>();
/*
        neighbourhood.add(new Position(1,0,0));
        neighbourhood.add(new Position(1,0,1));
        neighbourhood.add(new Position(1,0,-1));

        neighbourhood.add(new Position(-1,0,0));
        neighbourhood.add(new Position(-1,0,1));
        neighbourhood.add(new Position(-1,0,-1));

        neighbourhood.add(new Position(0,0,1));
        neighbourhood.add(new Position(0,0,-1));
*/

        neighbourhood.add(new Position(1,0,0));
        neighbourhood.add(new Position(-1,0,0));
        neighbourhood.add(new Position(0,0,1));
        neighbourhood.add(new Position(0,0,-1));
        neighbourhood.add(new Position(0,1,0));
        neighbourhood.add(new Position(0,-1,0));

        neighbourhood.add(new Position(2,0,0));
        neighbourhood.add(new Position(-2,0,0));
        neighbourhood.add(new Position(0,0,2));
        neighbourhood.add(new Position(0,0,-2));
        neighbourhood.add(new Position(0,2,0));
        neighbourhood.add(new Position(0,-2,0));

        neighbourhood.add(new Position(3,0,0));
        neighbourhood.add(new Position(-3,0,0));
        neighbourhood.add(new Position(0,0,3));
        neighbourhood.add(new Position(0,0,-3));
        neighbourhood.add(new Position(0,3,0));
        neighbourhood.add(new Position(0,-3,0));

        gameOfLife.setNeighbourhood(neighbourhood);

        //WorldManager.getInstance().setRule(gameOfLife);


        Thread worldThread = new Thread(WorldManager.getInstance());

        // Generate
        try {
            WorldGenerator.generate(0.4, 10, 10, 10);
        } catch (InvalidDensityException | InvalidDimensionException e) {
            e.printStackTrace();
        }

        worldThread.start();
        System.out.println("OK");

        WorldManager.getInstance().addObserver(ConsoleUI.getInstance());
        WorldManager.getInstance().addObserver(PopulationGraphGUI.getInstance());
        //WorldManager.getInstance().addObserver(VisualGUI.getInstance());

        // Launch all windows
        Application.launch(PopulationGraphGUI.class, args);
        //VisualGUI.main(args);
    }
}
