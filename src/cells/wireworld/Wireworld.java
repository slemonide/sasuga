package cells.wireworld;

import model.ActiveCell;
import model.Position;
import model.World;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A wireworld cell
 */
public abstract class Wireworld extends ActiveCell {
    private static final int WIREWORLD_DELAY = World.TICKS_PER_SECOND / 10;

    /**
     * Create a cell at the given position
     *
     * @param position position of this node
     */
    public Wireworld(Position position) {
        super(position);
        delay = WIREWORLD_DELAY;
    }
}
