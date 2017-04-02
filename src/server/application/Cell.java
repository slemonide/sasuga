package server.application;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an alive node.
 */
public class Cell {
    private Vector3 position;

    /**
     * Create a node at the given position
     * @param position
     */
    public Cell(Vector3 position) {
        this.position = position;
    }

    /**
     * Produce the position of this node
     * @return
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Produce the neighbours available so far
     * @return
     */
    public Set<Cell> getNeighbours() {
        Set<Cell> neighbours = new HashSet<Cell>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x != 0 && y != 0 && z != 0) {
                        if (WorldManager.getInstance().getCells().contains(new Cell(this.position.add(x, y, z)))) {
                            neighbours.add(new Cell(this.position.add(x, y, z)));
                        }
                    }
                }
            }
        }

        return neighbours;
    }

    public Set<Cell> getNeighboursComplement() {
        Set<Cell> neighboursComplement = new HashSet<Cell>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x != 0 && y != 0 && z != 0) {
                        if (!WorldManager.getInstance().getCells().contains(new Cell(this.position.add(x, y, z)))) {
                            neighboursComplement.add(new Cell(this.position.add(x, y, z)));
                        }
                    }
                }
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
