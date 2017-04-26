package model.wireworld;

import com.jme3.math.ColorRGBA;
import model.ActiveCell;
import model.Cell;
import model.Position;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A wireworld electron head
 */
public class ElectronHead extends ActiveCell {
    /**
     * Create a cell at the given position
     *
     * @param position position of this node
     */
    public ElectronHead(Position position) {
        super(position);
        color = ColorRGBA.Yellow;
    }

    @Override
    public void tick() {
        // do nothing
    }

    @Override
    public Collection<? extends Cell> tickToAdd() {
        Set<Cell> toAdd = new HashSet<>();
        toAdd.add(new ElectronTail(position));

        return toAdd;
    }

    @Override
    public Collection<? extends Position> tickToRemove() {
        return null;
    }
}
