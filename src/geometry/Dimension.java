package geometry;

import model.Position;

/**
 * Represents the dimensions of a 3D space
 */
public enum Dimension {
    X (Position.X),
    Y (Position.Y),
    Z (Position.Z);

    private final Position unitVector;

    Dimension(Position unitVector) {
        this.unitVector = unitVector;
    }

    public Position getUnitVector() {
        return unitVector;
    }

    public Dimension[] getComplements() {
        Dimension[] complements = new Dimension[2];

        switch (this) {
            case X:
                complements[0] = Y;
                complements[1] = Z;
                break;
            case Y:
                complements[0] = X;
                complements[1] = Z;
                break;
            default:
                complements[0] = X;
                complements[1] = Y;
                break;
        }

        return complements;
    }
}
