package model;

import com.jme3.math.Vector3f;

import static ui.Environment.SCALE;

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
    public Position add(int x, int y, int z) {
        return new Position(this.x + x, this.y + y, this.z + z);
    }

    public Position add(Position anotherVector) {
        return new Position(this.x + anotherVector.x, this.y + anotherVector.y, this.z + anotherVector.z);
    }

    /**
     * @return a vector representation of position in VisualGUI coordinates
     */
    public Vector3f getUIVector() {
        return this.multiply(SCALE);
    }

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

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    public Position subtract(Position position) {
        return this.add(position.inverse());
    }

    private Position inverse() {
        return new Position(-x, -y, -z);
    }
}
