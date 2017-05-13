package geometry;

import com.jme3.math.Vector3f;
import config.Options;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A three-dimensional position
 */
public final class Position {
    public static final Position X = new Position(1, 0, 0);
    public static final Position Y = new Position(0, 1, 0);
    public static final Position Z = new Position(0, 0, 1);
    public static final Position ORIGIN = new Position(0, 0, 0);
    private static final float SCALE = Options.getInstance().getFloat("SCALE");
    /**
     * x component
     */
    public final int x;
    /**
     * y component
     */
    public final int y;
    /**
     * z component
     */
    public final int z;

    /**
     * Creates a vector with the given coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Create a vector by adding given components to the components of this vector
     * @param x change in the x coordinate
     * @param y change in the y coordinate
     * @param z change in the z coordinate
     * @return the sum of this vector and given vector
     */
    @NotNull
    @Contract(pure = true)
    public Position add(int x, int y, int z) {
        return new Position(this.x + x, this.y + y, this.z + z);
    }

    @NotNull
    @Contract(pure = true)
    public Position add(Position anotherVector) {
        return new Position(this.x + anotherVector.x, this.y + anotherVector.y, this.z + anotherVector.z);
    }

    /**
     * @return a vector representation of position in VisualGUI coordinates
     */
    @NotNull
    @Contract(pure = true)
    public Vector3f getUIVector() {
        return this.multiply(SCALE);
    }

    @NotNull
    @Contract(pure = true)
    private Vector3f multiply(float scale) {
        return new Vector3f(scale * x, scale * y, scale * z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position vector3 = (Position) o;

        return x == vector3.x && y == vector3.y && z == vector3.z;
    }

    @Contract(pure = true)
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @NotNull
    @Contract(pure = true)
    public Position subtract(Position position) {
        return this.add(position.inverse());
    }

    @NotNull
    @Contract(pure = true)
    public Position inverse() {
        return new Position(-x, -y, -z);
    }

    public Position set(Axis axis, int value) {
        switch (axis) {
            case X:
                return new Position(value, y, z);
            case Y:
                return new Position(x, value, z);
            default:
                return new Position(x, y, value);
        }
    }

    public int get(Axis axis) {
        switch (axis) {
            case X:
                return x;
            case Y:
                return y;
            default:
                return z;
        }
    }

    public Position scale(int factor) {
        return new Position(factor * x, factor * y, factor * z);
    }

    public static Position fromUIVector(Vector3f vector) {
        return new Position(
                (int) (vector.x / SCALE),
                (int) (vector.y / SCALE),
                (int) (vector.z / SCALE));
    }

    /**
     * Produce the minimum of the two given positions
     * @param positionA first position
     * @param positionB second position
     * @return the position that is considered to be the smallest of the two given positions
     */
    public static Position min(Position positionA, Position positionB) {
        if ((positionA.x <= positionB.x) &&
                (positionA.y <= positionB.y) &&
                (positionA.z <= positionB.z)) {
            return positionA;
        } else {
            return positionB;
        }
    }
}