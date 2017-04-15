package server.ui;

import server.exceptions.InvalidDensityException;
import server.exceptions.InvalidDimensionException;
import server.model.*;
import server.rulesets.RandomWalk3;
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
public class ServerApp {
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
        neighbourhood.add(new Vector(0,0,1));

        neighbourhood.add(new Vector(-1,0,-1));
        neighbourhood.add(new Vector(-1,0,0));
        neighbourhood.add(new Vector(-1,0,1));

        gameOfLife.setNeighbourhood(neighbourhood);

        WorldManager.getInstance().setRule(gameOfLife);

        // Generate
        try {
            WorldGenerator.generate(0.6, 10, 1, 10);
        } catch (InvalidDensityException | InvalidDimensionException e) {
            e.printStackTrace();
        }

        System.out.println(WorldManager.getInstance().getPopulationSize());

        Thread clientManagerThread = new Thread(ClientManager.getInstance());
        clientManagerThread.start();

        System.out.println("OK");
    }
}
