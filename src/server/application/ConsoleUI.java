package server.application;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple console logging UI
 */
public class ConsoleUI implements Observer {
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
}
