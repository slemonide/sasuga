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
        while (!Thread.currentThread().isInterrupted()) {
            serveClient();
        }
    }

    private void serveClient() {
        try {
            String order = input.readLine();

            if (order.equals("GET CELLS")) {
                System.out.println("DEBUG: data request <GET CELLS> received");
                sendPackagedJsonCells();
            }

        } catch (IOException ignored) {}
    }

    private void sendPackagedJsonCells() {
        String jsonPackage = makeJsonPackage();
        output.println(jsonPackage);
        //output.println("[{\"Vector\":[0,1,1]}]");
    }

    private String makeJsonPackage() {
        StringBuilder message = new StringBuilder("[");
/*
        WorldManager.getInstance().add(new Cell(new Vector(0, 2, 0)));
        WorldManager.getInstance().add(new Cell(new Vector(0, 8, 0)));
        System.out.println(WorldManager.getInstance().getCells().size());
        for (Cell cell : WorldManager.getInstance().getRule().getCells()) {
            StringBuilder vectorString = new StringBuilder();
            int[] vector = cell.getPosition().v;
            for (int i = 0; i < vector.length; i++) {
                if (i != 0) {
                    vectorString.append(", ").append(vector[i]);
                } else {
                    vectorString.append(vector[i]);
                }
            }

            String cellAddition = "{" + "\"Vector :\"" + "[" + vectorString + "]" + "}";

            message.append(cellAddition).append(",");
        }
*/
        message.append("]");

        return message.toString();
    }
}
