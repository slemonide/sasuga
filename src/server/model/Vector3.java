package server.model;

import exceptions.InvalidDimensionException;

import java.util.Random;

/**
 *
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A vector in a 3D toroidal space (goodbye Euclidean space!)
 */
public class Vector3 {
    private static final int X_SIZE = 50;
    private static final int Y_SIZE = 50;
    private static final int Z_SIZE = 50;
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
    public Vector3(int x, int y, int z) {
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
    Vector3 add(int x, int y, int z) {
        return new Vector3(this.x + x, this.y + y, this.z + z);
    }

    Vector3 add(Vector3 anotherVector) {
        return new Vector3(this.x + anotherVector.x, this.y + anotherVector.y, this.z + anotherVector.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        return x == vector3.x && y == vector3.y && z == vector3.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
