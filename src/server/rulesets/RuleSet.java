package server.rulesets;

import server.model.Cell;
import server.model.Vector;

import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Interface for all rule sets
 */
public interface RuleSet {

    public void tick();

    Set<Cell> getCells();

    Set<Cell> getToAdd();

    Set<Cell> getToRemove();

    void add(Cell cell);

    Set<Vector> getNeighbourhood();

    void setLowerBound(int i);

    void setUpperBound(int i);

    void clear();

    void remove(Cell cell);

    void setNeighbourhood(Set<Vector> neighbourhood);
}
