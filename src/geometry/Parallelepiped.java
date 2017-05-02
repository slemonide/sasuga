package geometry;

import com.jme3.scene.Spatial;
import exceptions.GeometryMismatch;
import model.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a right-angled parallelepiped with integer-valued side lengths
 */
public final class Parallelepiped {
    private final Set<Position> positions;

    /**
     * Creates a unit parallelepiped centered at the given position
     * @param center center position of the parallelepiped
     */
    public Parallelepiped(Position center) {
        positions = new HashSet<>();
        positions.add(center);
    }

    /**
     * Merge this parallelepiped with another parallelepiped into a new parallelepiped
     * @return merged parallelepiped
     * @throws GeometryMismatch if parallelepipeds don't make a parallelepiped when merged
     */
    public Parallelepiped merge(Parallelepiped otherParallelepiped) throws GeometryMismatch {
        return this;
    }

    /**
     * @return positions of cells that this parallelepiped contains
     */
    public Set<Position> getPositions() {
        return positions;
    }

    /**
     * @return spatial representing this parallelepiped
     */
    public Spatial getSpatial() {
        return null;
    }

    public boolean contains(Position position) {
        return positions.contains(position);
    }
}
