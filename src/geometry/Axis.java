package geometry;


import lombok.Getter;
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

    @Getter private final Position unitVector;

    Axis(Position unitVector) {
        this.unitVector = unitVector;
    }

    public Set<Axis> getComplements() {
        Set<Axis> complements = new HashSet<>();
        Collections.addAll(complements, Axis.values());
        complements.remove(this);

        return complements;
    }
}
