package model;

import com.jme3.math.ColorRGBA;

import java.util.Observable;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a cell with position and name.
 */
public class Cell extends Observable {
    private String name = "Static Cell";
    protected Position position;
    protected ColorRGBA color;

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

    public void setName(String name) {
        this.name = name;
    }

    public ColorRGBA getColor() {
        return color;
    }
}
