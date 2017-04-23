package model;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a cell that modify world.
 */
public abstract class ActiveCell extends Cell {
    /**
     * Create a cell at the given position
     *
     * @param position position of this node
     */
    public ActiveCell(Position position) {
        super(position);
    }

    /**
     * Changes this Cell
     */
    public abstract void tick();
}
