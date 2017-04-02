package server.application;

import com.jme3.math.Vector3f;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an alive node.
 */
public class Cell {
    private Vector3f position;
    private Set<Cell> neighbours;

    /**
     * Create a node at the given position
     * @param position
     */
    public Cell(Vector3f position) {
        this.position = position;
        this.neighbours = new HashSet<Cell>();
    }

    public Cell(Vector3f position, Set<Cell> neighbours) {
        this.position = position;
        this.neighbours = neighbours;
    }

    /**
     * Produce the position of this node
     * @return
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Produce the neighbours available so far
     * @return
     */
    public Set<Cell> getNeighbours() {
        return neighbours;
    }
}
