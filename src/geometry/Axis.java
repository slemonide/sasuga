package geometry;

import model.Position;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the dimensions of a 3D space
 */
public enum Axis {
    X (Position.X),
    Y (Position.Y),
    Z (Position.Z);

    private final Position unitVector;

    Axis(Position unitVector) {
        this.unitVector = unitVector;
    }

    public Position getUnitVector() {
        return unitVector;
    }

    public Set<Axis> getComplements() {
        Set<Axis> complements = new HashSet<>();
        Collections.addAll(complements, Axis.values());
        complements.remove(this);

        return complements;
    }
}
