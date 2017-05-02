package geometry;

import model.Position;

/**
 * Represents the dimensions of a 3D space
 */
public enum Dimension {
    X, Y, Z;

    public Position[] getDirections() {
        Position[] directions = new Position[2];
        switch (this) {
            case X:
                directions[0] = new Position(1,0,0);
                directions[1] = new Position(-1,0,0);
                break;
            case Y:
                directions[0] = new Position(0,1,0);
                directions[1] = new Position(0,-1,0);
                break;
            default:
                directions[0] = new Position(0,0,1);
                directions[1] = new Position(0,0,-1);
        }

        return directions;
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
