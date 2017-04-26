package model.wireworld;

import com.jme3.math.ColorRGBA;
import model.ActiveCell;
import model.Cell;
import model.Position;

import java.util.Collection;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A wireworld conductor
 */
public class Conductor extends ActiveCell {
    /**
     * Create a cell at the given position
     *
     * @param position position of this cell
     */
    public Conductor(Position position) {
        super(position);
        color = ColorRGBA.Blue;
    }

    @Override
    public void tick() {
        // do nothing
    }

    @Override
    public Collection<? extends Cell> tickToAdd() {
        // stub

        return null;
    }

    @Override
    public Collection<? extends Position> tickToRemove() {
        return null;
    }
}
