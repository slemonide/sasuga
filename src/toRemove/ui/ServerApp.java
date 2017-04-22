package toRemove.ui;

import server.model.*;

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

        Thread clientManagerThread = new Thread(ClientManager.getInstance());
        clientManagerThread.start();

        System.out.println("OK");
    }
}
