package toRemove.ui;

import server.exceptions.InvalidDensityException;
import server.exceptions.InvalidDimensionException;
import server.model.Position;
import server.generator.WorldGenerator;
import server.model.World;
import server.rules.ThirdTimeDimension;
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
public class ServerAppGameOfLifeRulesThirdTimeDimension {
    public static void main(String[] args) {
        System.out.print("Starting server... ");

        ThirdTimeDimension gameOfLife = new ThirdTimeDimension();

        gameOfLife.setLowerBound(1);
        gameOfLife.setUpperBound(3);

        Set<Position> neighbourhood = new HashSet<>();

        neighbourhood.add(new Position(1,0,-1));
        neighbourhood.add(new Position(1,0,0));
        neighbourhood.add(new Position(1,0,1));

        neighbourhood.add(new Position(0,0,-1));
        //neighbourhood.add(new Position(0,0,0));
        neighbourhood.add(new Position(0,0,1));

        neighbourhood.add(new Position(-1,0,-1));
        neighbourhood.add(new Position(-1,0,0));
        neighbourhood.add(new Position(-1,0,1));

        gameOfLife.setNeighbourhood(neighbourhood);

        //World.getInstance().setRule(gameOfLife);

        Thread worldThread = new Thread(World.getInstance());

        // Generate
        try {
            WorldGenerator.generate(0.3, 15, 1, 15);
        } catch (InvalidDensityException | InvalidDimensionException e) {
            e.printStackTrace();
        }

// glider
        /*
        World.getInstance().add(new StaticCell(new Position(0, 0, 1)));
        World.getInstance().add(new StaticCell(new Position(0, 0, 2)));
        World.getInstance().add(new StaticCell(new Position(0, 0, 3)));
        World.getInstance().add(new StaticCell(new Position(2, 0, 2)));
        World.getInstance().add(new StaticCell(new Position(1, 0, 1)));
        */

/*
        // the bottom left thing
        World.getInstance().add(new StaticCell(new Position(1, 0,2)));
        World.getInstance().add(new StaticCell(new Position(2, 0,2)));
        World.getInstance().add(new StaticCell(new Position(2, 0,3)));

        // other thing
        World.getInstance().add(new StaticCell(new Position(7, 0,1)));
        World.getInstance().add(new StaticCell(new Position(6, 0,3)));
        World.getInstance().add(new StaticCell(new Position(7, 0,3)));
        World.getInstance().add(new StaticCell(new Position(8, 0,3)));*/


        //System.out.println(World.getInstance().getRule().getCells().size());

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
