package server;

import server.model.ClientManager;
import server.model.World;
import server.ui.ConsoleUI;

/**
 * Starts the server
 */
public class Main {
    public static void main(String[] args) {
        System.out.print("Starting server... ");

        // initialize observers
        World.getInstance().addObserver(ConsoleUI.getInstance());

        // initialize threads
        Thread clientManagerThread = new Thread(ClientManager.getInstance());
        clientManagerThread.start();

        System.out.println("OK");
    }
}
