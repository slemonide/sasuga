package client.ui;

import client.model.ConfigManager;

import java.util.Scanner;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A basic text UI to communicate with the server
 */
public class TextUI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ConfigManager.getInstance();

        boolean running = true;
        while (running) {
            System.out.print("==> ");
            String nextLine = input.nextLine();

            System.out.println(nextLine);
            if (nextLine.equals("q")) {
                running = false;
            }
        }
    }
}
