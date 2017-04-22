package client.model;

import client.parsers.CellsParser;
import server.model.Cell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Manages the server-client connection
 */
public class ServerManager {
    public static final String SERVER = "127.0.0.1";
    private static final int PORT = 4444;

    BufferedReader output;
    PrintStream input;

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

    /**
     * Connects to the default server
     */
    public void connect() throws IOException {
        Socket sock = new Socket(SERVER, PORT);
        output = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        input = new PrintStream(sock.getOutputStream());

        input.println("myName");
        System.out.println(output.readLine());
    }

    public Set<Cell> getCells() throws IOException {
        input.println("GET CELLS");
        String jsonCells = output.readLine();
        System.out.println("Data received:");
        System.out.println(jsonCells);
        CellsParser cellsParser = new CellsParser(jsonCells);
        return cellsParser.getCells();
    }
}
