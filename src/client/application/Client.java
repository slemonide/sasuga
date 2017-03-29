package client.application;


import java.io.*;
import java.net.*;

class Client {
    public static void main(String a[]) throws IOException {
        String server = "127.0.0.1";
        Socket sock = new Socket(server, 4444);

        BufferedReader dis = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintStream dat = new PrintStream(sock.getOutputStream());

        while (!sock.isClosed()) {
            dat.println("PING");

            System.out.println(dis.readLine());
        }
    }
}