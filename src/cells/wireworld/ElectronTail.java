package cells.wireworld;

import com.jme3.math.ColorRGBA;
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
 * A wireworld electron tail
 */
public class ElectronTail extends Wireworld {
    /**
     * Create a cell at the given position
     *
     * @param position position of this node
     */
    public ElectronTail(Position position) {
        super(position);
        color = ColorRGBA.Red;
    }

    @Override
    public void tick() {

    }

    @Override
    public Collection<? extends Cell> tickToAdd() {
        Set<Cell> toAdd = new HashSet<>();
        toAdd.add(new Conductor(position));

        return toAdd;
    }

    @Override
    public Collection<? extends Position> tickToRemove() {
        return null;
    }
}
