package server.model;

import java.io.*;
import java.net.Socket;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a client
 */
public class Client implements Runnable {
    private InputStream inputStream;
    private OutputStream outputStream;
    private BufferedReader input;
    private PrintStream output;

    private String name;

    public Client(Socket clientSocket) throws IOException {
        inputStream = clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();
        input = new BufferedReader(new InputStreamReader(inputStream));
        output = new PrintStream(outputStream);

        // debug
        name = input.readLine();

        output.println("Hello, " + name);
        System.out.println("Hello, " + name);
        // end debug
    }

    @Override
    public void run() {

    }
}
