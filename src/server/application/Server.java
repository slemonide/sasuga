package server.application;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String a[]) throws IOException {
        int port = 4444;
        Socket client;
        ServerSocket servsock = new ServerSocket(port);

        client = servsock.accept();

        PrintStream out = new PrintStream(client.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        while (true) {
            if (in.readLine().equals("PING")) {
                out.println("PONG");
            } else {
                out.println("ERROR");
            }

            out.flush();
        }
    }
}
