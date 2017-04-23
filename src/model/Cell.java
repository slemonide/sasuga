package model;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a static cell that never changes on its own.
 */
public class Cell {
    protected Position position;

    /**
     * Create a cell at the given position
     * @param position position of this node
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
}
