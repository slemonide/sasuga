package client;

import client.model.ServerManager;
import toRemove.ui.VisualGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Starts the client
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ServerManager.getInstance().connect();
        VisualGUI.main(args);
    }
}
