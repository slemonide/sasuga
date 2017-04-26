package model.wireworld;

import com.jme3.math.ColorRGBA;
import model.Cell;
import model.Position;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A wireworld conductor
 */
public class Conductor extends Cell {
    /**
     * Create a cell at the given position
     *
     * @param position position of this cell
     */
    public Conductor(Position position) {
        super(position);
        color = ColorRGBA.Blue;
    }
}
