package model;

import java.util.Observable;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a cell that, by default never changes on its own.
 */
public class Cell extends Observable {
    private String name = "Static Cell";
    protected Position position;

    /**
     * Create a cell at the given position
     * @param position position of this cell
     */
    public Cell(Position position) {
        this.position = position;
    }

    /**
     * Return the position of this cell
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
