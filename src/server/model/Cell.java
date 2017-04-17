package server.model;

import server.rulesets.Rule;

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
    private Vector position;
    private Rule rule;

    /**
     * Create a cell at the given position and with the "static" ruleset
     * @param position position of this node in the global coordinates
     */
    public Cell(Vector position) {
        this.position = position;
    }

    /**
     * Produce the position of this cell
     * @return position
     */
    public Vector getPosition() {
        return position;
    }

    /**
     * Produce the neighbours available so far
     * @return neighbour cells
     */
    public Set<Cell> getNeighbours() {
        Set<Cell> neighbours = new HashSet<>();
        for (Vector neighbour : WorldManager.getInstance().getRule().getNeighbourhood()) {
            if (WorldManager.getInstance().getRule().getCells().contains(new Cell(this.position.add(neighbour)))) {
                neighbours.add(new Cell(this.position.add(neighbour)));
            }
        }

        return neighbours;
    }

    /**
     * Produce all the possible neighbours minus neighbours available so far
     * @return complement of the neighbour cells set
     */
    public Set<Cell> getNeighboursComplement() {
        Set<Cell> neighboursComplement = new HashSet<>();
        for (Vector neighbour : WorldManager.getInstance().getRule().getNeighbourhood()) {
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
