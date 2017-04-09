package client.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a client
 */
public class Client {
    public static final String SERVER = "127.0.0.1";
    private static final int PORT = 4444;

    public static void main(String[] args) throws IOException {
        Socket sock = new Socket(SERVER, PORT);

        BufferedReader dis = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintStream dat = new PrintStream(sock.getOutputStream());

        dat.println("myName");
        System.out.println(dis.readLine());
    }
}
