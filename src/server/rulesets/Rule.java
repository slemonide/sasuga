package server.rulesets;

import server.model.Cell;
import server.model.Position;

import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Interface for all rule sets
 */
public interface Rule {

    void tick();

    Set<Cell> getCells();

    Set<Cell> getToAdd();

    Set<Cell> getToRemove();

    void add(Cell cell);

    Set<Position> getNeighbourhood();

    void clear();

    void remove(Cell cell);
}
