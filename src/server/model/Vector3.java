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

    /**
     * Generate a random vector [x_0, y_0, z_0]
     * <p>
     *     produced vector is part of the following set:
     *     {[x_0, y_0, z_0] : x - x/2 <= x_0 <= x/2,
     *                        y - y/2 <= y_0 <= y/2
     *                        z - z/2 <= z_0 <= z/2}
     * </p>
     * @param x x - x/2 <= x_0 <= x/2,
     * @param y y - y/2 <= y_0 <= y/2,
     * @param z z - z/2 <= z_0 <= z/2,
     * @return a random vector
     * @throws InvalidDimensionException if x, y, or z is negative
     */
    static Vector3 getRandomVector(int x, int y, int z) throws InvalidDimensionException {
        if (x < 0 || y < 0 || z < 0) {
            throw new InvalidDimensionException();
        }

        Random random = new Random();

        int x_0 = random.nextInt(x/2 + 1) - (x - x/2);
        int y_0 = random.nextInt(y/2 + 1) - (y - y/2);
        int z_0 = random.nextInt(z/2 + 1) - (z - z/2);
        return new Vector3(x_0, y_0, z_0);
    }
}
