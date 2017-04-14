package server.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author      Danil Platonov <slemonide@gmail.com>, jacketsj <jacketsj@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A vector in a 3D toroidal space (goodbye Euclidean space!)
 */
public class Vector {
    public static final int SIZE = 50;
    //private static final int X_SIZE = 50;
    //private static final int Y_SIZE = 50;
    //private static final int Z_SIZE = 50;
    /**
     * components
     */
    public final int[] v;

    /**
     * Creates a vector with the given coordinates
     * @param coordinates
     */
    public Vector(int... coordinates) {
        v = new int[coordinates.length];
        for (int i = 0; i < coordinates.length; ++i) {
            v[i] = coordinates[i]; //keep it final
        }
    }

    public Vector(List<Integer> coordinates) {
        v = new int[coordinates.size()];
        for (int i = 0; i < coordinates.size(); ++i) {
            v[i] = coordinates.get(i); //keep it final
        }
    }

    /**
     * Create a vector by adding given components to the components of this vector
     * @param cooordinates
     * @return the sum of this vector and given vector
     */
    public Vector add(int... cooordinates) {
        Vector givenVector = new Vector(cooordinates);

        return this.add(givenVector);
    }

    public Vector add(Vector anotherVector) {
        int max = Math.max(v.length, anotherVector.v.length);
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

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
        return result;
    }
}
