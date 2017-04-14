package server.model;

import java.util.Collections;

/**
 *
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A vector in a 3D toroidal space (goodbye Euclidean space!)
 */
public class Vector {
    private static final int SIZE = 50;
    //private static final int X_SIZE = 50;
    //private static final int Y_SIZE = 50;
    //private static final int Z_SIZE = 50;
    /**
     * components
     */
    public final int[] v;

    /**
     * Creates a vector with the given coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Vector(int x, int y, int z) {
        v = new int[3];
        v[0] = x;
        v[1] = y;
        v[2] = z;
    }

    /**
     * Creates a vector with the given coordinates
     * @param V coordinate array
     */
    public Vector(int[] V) {
        v = new int[V.length];
        for (int i = 0; i < V.length; ++i) {
            v[i] = V[i]; //keep it final
        }
    }

    /**
     * Create a vector by adding given components to the components of this vector
     * @param x change in the x coordinate
     * @param y change in the y coordinate
     * @param z change in the z coordinate
     * @return the sum of this vector and given vector
     */
    public Vector add(int x, int y, int z) {
        int[] nexyz = new int[v.length];
        int[] xyz = new int[3];
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
        int n = 3;
        if (v.length < n) {
            n = v.length;
        }
        for (int i = 0; i < 3; ++i) {
            nexyz[i] = xyz[i] + v[i];
        }
        return new Vector(nexyz);
        //return new Vector(this.x + x, this.y + y, this.z + z);
    }

    public Vector add(Vector anotherVector) {
        int max = v.length;
        if (anotherVector.v.length > max) {
            max = anotherVector.v.length;
        }
        int[] newCoords = new int[max];
        for (int i = 0; i < max; ++i) {
            int a = 0;
            int b = 0;
            if (i < v.length)
            {
                a = v[i];
            }
            if (i < anotherVector.v.length)
            {
                b = anotherVector.v[i];
            }
            newCoords[i]=a+b;
        }
        return new Vector(newCoords);
        //return new Vector(this.x + anotherVector.x, this.y + anotherVector.y, this.z + anotherVector.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        //return x == vector.x && y == vector.y && z == vector.z;
        if (v.length != vector.v.length) {
            return false;
        }

        for (int i = 0; i < v.length; ++i) {
            if (v[i] != vector.v[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int c : v) {
            result = (31 * result + c) % 64;
        }
        //int result = x;
        //result = 31 * result + y;
        //result = 31 * result + z;
        return result;
    }
}
