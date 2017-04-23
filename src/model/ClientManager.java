package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages clients
 */
public class ClientManager implements Runnable, Observer {
    public static final int PORT = 4444;

    private static ClientManager instance;

    private ServerSocket serverSocket;
    private Set<Client> clients;

    private ClientManager() {
        clients = new HashSet<>();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.printf("ERROR: cannot create a server socket");
            e.printStackTrace();
        }
    }

    /**
     * Singleton pattern
     * @return instance
     */
    public static ClientManager getInstance() {
        if(instance == null){
            instance = new ClientManager();
        }
        return instance;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            handleClients();
        }
    }

    private void handleClients() {
        try {
            Socket clientSocket = serverSocket.accept();
            Client client = new Client(clientSocket);
            Thread clientThread = new Thread(client);
            clientThread.start();
            clients.add(client);
        } catch (IOException e) {
            System.out.println("ERROR: cannot accept a client socket");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("tick")) {
            notifyClients();
        }
    }

    private void notifyClients() {
        for (Client client : clients) {
            //client.send(); // send updates
        }
    }
}
