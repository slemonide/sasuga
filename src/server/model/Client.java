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
                sendPackagedJsonCells();
            }

        } catch (IOException ignored) {}
    }

    private void sendPackagedJsonCells() {
        String jsonPackage = makeJsonPackage();
        System.out.print(jsonPackage);
        output.print(jsonPackage);
    }

    private String makeJsonPackage() {
        StringBuilder message = new StringBuilder("[");

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

        message.append("]");

        return message.toString();
    }
}
