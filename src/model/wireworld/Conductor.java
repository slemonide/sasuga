package model.wireworld;

import com.jme3.math.ColorRGBA;
import model.ActiveCell;
import model.Cell;
import model.Position;
import model.World;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A wireworld conductor
 */
public class Conductor extends ActiveCell {
    /**
     * Create a cell at the given position
     *
     * @param position position of this cell
     */
    public Conductor(Position position) {
        super(position);
        color = ColorRGBA.Blue;
    }

    @Override
    public void tick() {
        // do nothing
    }

    @Override
    public Collection<? extends Cell> tickToAdd() {
        int numberOfAdjacentElectronHeads = 0;

        for (Position neighbour : getActiveNeighbours(position)) {
            if (World.getInstance().getCellsMap().get(neighbour) instanceof ElectronHead) {
                numberOfAdjacentElectronHeads++;
            }
        }

        if (numberOfAdjacentElectronHeads == 1 || numberOfAdjacentElectronHeads == 2) {
            Set<Cell> toAdd = new HashSet<>();
            toAdd.add(new ElectronHead(position));
            return toAdd;
        } else {
            return null;
        }
    }

    private Set<Position> getActiveNeighbours(Position position) {
        // stub: for now, just return the neighbours in 3-space
        // TODO: generalize for arbitrary number of dimensions
        Set<Position> neighbours = new HashSet<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    neighbours.add(position.add(x, y, z));
                }
            }
        }

        return neighbours;
    }

    @Override
    public Collection<? extends Position> tickToRemove() {
        return null;
    }
}
