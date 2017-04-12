package server.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents an alive node.
 */
public class Cell {
    /**
     * Position of this cell
     */
    private Vector3 position;

    /**
     * Create a node at the given position
     * @param position position of this node in the global coordinates
     */
    public Cell(Vector3 position) {
        this.position = position;
    }

    /**
     * Produce the position of this node
     * @return position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Produce the neighbours available so far
     * @return neighbour cells
     */
    public Set<Cell> getNeighbours() {
        Set<Cell> neighbours = new HashSet<Cell>();
        for (Vector3 neighbour : WorldManager.getInstance().getRule().getNeighbourhood()) {
            if (WorldManager.getInstance().getRule().getCells().contains(new Cell(this.position.add(neighbour)))) {
                neighbours.add(new Cell(this.position.add(neighbour)));
            }
        }

        return neighbours;
    }
/*
    static private Set<Vector3> getNeighbourhood() {
        Set<Vector3> neighbourhood = new HashSet<>();

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (x != 0 && z != 0) {
                    neighbourhood.add(new Vector3(x, 0, z));
                }
            }
        }
*/
/*
        for (int x = - RADIUS_OUT; x <= RADIUS_OUT; x++) {
            for (int y = - RADIUS_OUT; y <= RADIUS_OUT; y++) {
                for (int z = - RADIUS_OUT; z <= RADIUS_OUT; z++) {
                    if (x != 0 && y != 0 && z != 0) {
                        neighbourhood.add(new Vector3(x, y, z));
                    }
                }
            }
        }

        for (int x = - RADIUS_IN; x <= RADIUS_IN; x++) {
            for (int y = - RADIUS_IN; y <= RADIUS_IN; y++) {
                for (int z = - RADIUS_IN; z <= RADIUS_IN; z++) {
                    if (x != 0 && y != 0 && z != 0) {
                        neighbourhood.remove(new Vector3(x, y, z));
                    }
                }
            }
        }
*/
      //  return neighbourhood;
    //}

    /**
     * Produce all the possible neighbours minus neighbours available so far
     * @return complement of the neighbour cells set
     */
    public Set<Cell> getNeighboursComplement() {
        Set<Cell> neighboursComplement = new HashSet<Cell>();
        for (Vector3 neighbour : WorldManager.getInstance().getRule().getNeighbourhood()) {
            if (!WorldManager.getInstance().getRule().getCells().contains(new Cell(this.position.add(neighbour)))) {
                neighboursComplement.add(new Cell(this.position.add(neighbour)));
            }
        }
        return neighboursComplement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return position.equals(cell.position);
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }
}
