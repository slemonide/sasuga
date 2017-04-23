package cells;

import model.Cell;
import model.Position;

import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Interface for all rule sets
 */
public interface ActiveRule {

    void tick();

    Set<Cell> getCells();

    Set<Cell> getToAdd();

    Set<Cell> getToRemove();

    void add(Cell cell);

    Set<Position> getNeighbourhood();

    void clear();

    void remove(Cell cell);
}
