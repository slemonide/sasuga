package server.ui;

import server.exceptions.InvalidDensityException;
import server.exceptions.InvalidDimensionException;
import server.model.Vector;
import server.model.WorldGenerator;
import server.model.WorldManager;
import server.rulesets.ThirdTimeDimension;

import java.util.HashSet;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Main app
 */
public class ServerAppGameOfLifeRulesThirdTimeDimension {
    public static void main(String[] args) {
        System.out.print("Starting server... ");

        ThirdTimeDimension gameOfLife = new ThirdTimeDimension();

        gameOfLife.setLowerBound(1);
        gameOfLife.setUpperBound(3);

        Set<Vector> neighbourhood = new HashSet<>();

        neighbourhood.add(new Vector(1,0,-1));
        neighbourhood.add(new Vector(1,0,0));
        neighbourhood.add(new Vector(1,0,1));

        neighbourhood.add(new Vector(0,0,-1));
        //neighbourhood.add(new Vector(0,0,0));
        neighbourhood.add(new Vector(0,0,1));

        neighbourhood.add(new Vector(-1,0,-1));
        neighbourhood.add(new Vector(-1,0,0));
        neighbourhood.add(new Vector(-1,0,1));

        gameOfLife.setNeighbourhood(neighbourhood);

        WorldManager.getInstance().setRule(gameOfLife);

        Thread worldThread = new Thread(WorldManager.getInstance());

        // Generate
        try {
            WorldGenerator.generate(0.2, 10, 1, 10);
        } catch (InvalidDensityException | InvalidDimensionException e) {
            e.printStackTrace();
        }

/*
        // the bottom left thing
        WorldManager.getInstance().add(new Cell(new Vector(1, 0,2)));
        WorldManager.getInstance().add(new Cell(new Vector(2, 0,2)));
        WorldManager.getInstance().add(new Cell(new Vector(2, 0,3)));

        // other thing
        WorldManager.getInstance().add(new Cell(new Vector(7, 0,1)));
        WorldManager.getInstance().add(new Cell(new Vector(6, 0,3)));
        WorldManager.getInstance().add(new Cell(new Vector(7, 0,3)));
        WorldManager.getInstance().add(new Cell(new Vector(8, 0,3)));*/


        System.out.println(WorldManager.getInstance().getRule().getCells().size());

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
