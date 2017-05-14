package cells.GameOfLife;

import cells.ActiveCell;
import cells.Cell;
import geometry.Position;

import java.util.Collection;

/**
 * A game of life cell
 */
public class LifeCell extends ActiveCell {
    /**
     * Create a cell at the given position
     *
     * @param position position of this node
     */
    public LifeCell(Position position) {
        super(position);
    }

    /**
     * Called every new generation
     */
    @Override
    public void tick() {

    }

    /**
     * @return cells to be added in the next tick
     */
    @Override
    public Collection<? extends Cell> tickToAdd() {
        return null;
    }

    /**
     * @return positions to be cleaned up in the next tick
     */
    @Override
    public Collection<? extends Position> tickToRemove() {
        return null;
    }
}
