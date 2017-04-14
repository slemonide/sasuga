package server.ui;

import server.model.ClientManager;
import server.model.WorldManager;
import server.rulesets.RandomWalk3;

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
        WorldManager.getInstance().setRule(new RandomWalk3());

        Thread clientManagerThread = new Thread(ClientManager.getInstance());
        clientManagerThread.start();

        System.out.println("OK");
    }
}
