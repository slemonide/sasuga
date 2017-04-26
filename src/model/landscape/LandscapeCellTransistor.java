package model.landscape;

import model.ActiveCell;
import model.Cell;
import model.Position;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LandscapeCellTransistor extends ActiveCell {
    private static final int TICK_DELAY = 500;
    private int delay;
    /**
     * Create a cell at the given position
     *
     * @param position position of this node
     */
    LandscapeCellTransistor(Position position) {
        super(position);
        delay = 0;
    }

    @Override
    public void tick() {
        delay++;
    }

    @Override
    public Collection<? extends Cell> tickToAdd() {
        if (delay > TICK_DELAY) {
            delay = 0;
            
            Set<Cell> nextCells = new HashSet<>();
            nextCells.add(new LandscapeCellZ(position));

            return nextCells;
        }

        return null;
    }

    @Override
    public Collection<? extends Position> tickToRemove() {
        return null;
    }
}
