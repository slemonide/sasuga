package server.application;

import java.util.Observable;
import java.util.Observer;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A simple console logging UI
 */
public class ConsoleUI implements Observer {
    public void update(Observable o, Object arg) {
        System.out.println(arg);
    }
}
