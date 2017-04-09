package server.model;

import java.util.Observable;
import java.util.Observer;

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

    private ClientManager() {}

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

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("tick")) {
            notifyClients();
        }
    }

    private void notifyClients() {

    }
}
