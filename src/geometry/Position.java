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
    /**
     * The X direction
     */
    public static final Position X = new Position(1, 0, 0);
    /**
     * The Y direction
     */
    public static final Position Y = new Position(0, 1, 0);
    /**
     * The Z direction
     */
    public static final Position Z = new Position(0, 0, 1);
    /**
     * The place where the counting begins
     */
    public static final Position ORIGIN = new Position(0, 0, 0);
    /**
     * Scale of the world relative to the positions basis
     */
    public static final float SCALE = Options.getInstance().getFloat("SCALE");
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
     * Constructs a position from the given Vector3f
     * @param vector a Vector3f used in the graphical engine
     * @return abstract position used by the game mechanics
     */
    @NotNull
    public static Position fromUIVector(Vector3f vector) {
        return new Position(
                (int) (vector.x / SCALE),
                (int) (vector.y / SCALE),
                (int) (vector.z / SCALE));
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

    /**
     * Adds this position to that position
     * @param that other position
     * @return sum of this position and that position
     */
    @NotNull
    @Contract(pure = true)
    public Position add(Position that) {
        return new Position(this.x + that.x, this.y + that.y, this.z + that.z);
    }

    /**
     * Produces a representation of this position in VisualGUI format
     * @return a representation of this position in VisualGUI format
     */
    @NotNull
    @Contract(pure = true)
    public Vector3f getUIVector() {
        // TODO: this might be the cause of the weird cursor behaviour
        return this.multiply(SCALE);
    }

    /**
     * Multiply this position by the given scalar (float) factor
     * @param factor a scalar (float) factor
     * @return position that has each component multiplied by the given factor
     */
    @NotNull
    @Contract(pure = true)
    private Vector3f multiply(float factor) {
        return new Vector3f(factor * x, factor * y, factor * z);
    }

    /**
     * Multiply this position by the given scalar (int) factor
     * @param factor a scalar (int) factor
     * @return position that has each component multiplied by the given factor
     */
    @NotNull
    @Contract(pure = true)
    public Position multiply(int factor) {
        return new Position(factor * x, factor * y, factor * z);
    }

    /**
     * Produce true if all components of this and that positions are equal
     * @param o another object
     * @return true if the other object is a Position and components are equal
     */
    @Contract(pure = true)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position vector3 = (Position) o;

        return x == vector3.x && y == vector3.y && z == vector3.z;
    }

    /**
     * Produce a hashcode so that positions with the same components
     * have the same hashcodes
     * @return hashcode generated from the components of this position
     */
    @Contract(pure = true)
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    /**
     * Subtract that position from this position
     * @param that position which to subtract
     * @return this position minus the given position
     */
    @NotNull
    @Contract(pure = true)
    public Position subtract(Position that) {
        return this.add(that.inverse());
    }

    /**
     * Produce the additive inverse of this position
     * @return additive inverse of this position
     */
    @NotNull
    @Contract(pure = true)
    public Position inverse() {
        return new Position(-x, -y, -z);
    }

    /**
     * Sets the given component to the given value
     * @param axis component to set
     * @param value the value to which to set the component
     * @return this position with the given component replaced by the value
     */
    @NotNull
    @Contract(pure = true)
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

    /**
     * Produces the requested component of this position
     * @param axis requested component
     * @return the value of the requested component
     */
    @Contract(pure = true)
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

    /**
     * Produce the minimum of this and that position
     *
     * <p>
     *     Minimum position is determined by the following total order over the
     *     set of all positions:
     *
     *     Position A is less than or equal to position B if:
     *     (A.x <= B.x) &&
     *     (A.y <= B.y) &&
     *     (A.z <= B.z)
     * </p>
     * @param that the other position
     * @return the position that is considered to be the smallest of the two given positions
     */
    @Contract(pure = true)
    @NotNull
    public Position min(@NotNull Position that) {
        if (lessThanOrEqual(that)) {
            return this;
        } else {
            return that;
        }
    }


    /**
     * Produces true if this position is less than or equal to that position
     *
     * @see #min(Position)
     *
     * @param that the other position
     * @return true, if this position is less than or equal to that position,
     * false otherwise
     */
    @Contract(pure = true)
    public boolean lessThanOrEqual(@NotNull Position that) {
        return (x <= that.x) &&
                (y <= that.y) &&
                (z <= that.z);
    }

    /**
     * Divides each component of this position by the given number
     * @param factor the divisor
     * @return position with each component divided by the factor
     */
    @NotNull
    public Position divide(int factor) {
        return new Position(x / factor, y / factor, z / factor);
    }
}
