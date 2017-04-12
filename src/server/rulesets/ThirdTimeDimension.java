package server.rulesets;

import server.model.Cell;
import server.model.Vector3;

import java.util.HashSet;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a rule set where y-dimension is used to represent time flow
 */
public class ThirdTimeDimension extends NeighbourhoodCellular {
    private Set<Cell> activeCells;

    public ThirdTimeDimension() {
        super();

        activeCells = new HashSet<>();
    }

    @Override
    public void tick() {
        cells.addAll(activeCells);

        Set<Cell> toAdd = super.grow(activeCells);
        Set<Cell> toKill = super.die(activeCells);

        activeCells.addAll(toAdd);
        activeCells.removeAll(toKill);

        activeCells = bumpUp(activeCells);

        // update populationSize
        populationSize += toAdd.size();
        populationSize -= toKill.size();
    }

    @Override
    public void add(Cell cell) {
        activeCells.add(cell);
        populationSize++;
    }

    private Set<Cell> bumpUp(Set<Cell> cells) {
        Set<Cell> nextCells = new HashSet<>();

        for (Cell cell : cells) {
            nextCells.add(new Cell(new Vector3(
                    cell.getPosition().x,
                    cell.getPosition().y + 1,
                    cell.getPosition().z)));
        }

        return nextCells;
    }
}
