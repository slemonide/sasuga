package client.model;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages the server-client connection
 */
public class ServerManager {
    private static ServerManager instance;

    private ServerManager() {}

    /**
     * Singleton pattern
     * @return instance
     */
    public static ServerManager getInstance() {
        if(instance == null){
            instance = new ServerManager();
        }
        return instance;
    }
}
