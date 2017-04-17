package server.rulesets;

import server.model.Cell;
import server.model.Vector;

import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A ruleset for static cells that do nothing on tick()
 */
public class Stable implements Rule {
    @Override
    public void tick() {
        // do nothing
    }

    @Override
    public Set<Cell> getCells() {
        return null;
    }

    @Override
    public Set<Cell> getToAdd() {
        return null;
    }

    @Override
    public Set<Cell> getToRemove() {
        return null;
    }

    @Override
    public void add(Cell cell) {

    }

    @Override
    public Set<Vector> getNeighbourhood() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void remove(Cell cell) {

    }
}
