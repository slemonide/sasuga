package server.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author      Danil Platonov <slemonide@gmail.com>, jacketsj <jacketsj@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A geometrical vector
 */
public class Vector {
    /**
     * components
     */
    private Map<Integer, Integer> components;

    /**
     * Creates a vector with the given coordinates
     * @param coordinates coordinates of the given vector
     */
    public Vector(int... coordinates) {
        components = new HashMap<>();

        for (int i = 0; i < coordinates.length; ++i) {
            components.put(i, coordinates[i]);
        }
    }

    public Vector(List<Integer> coordinates) {
        components = new HashMap<>();

        for (int i = 0; i < coordinates.size(); ++i) {
            components.put(i, coordinates.get(i));
        }
    }

    public Vector(Map<Integer, Integer> coordinates) {
        // Make a private copy of the given coordinates
        components = coordinates.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Create a vector by adding given components to the components of this vector
     * @param coordinates coordinates of the given vector
     * @return the sum of this vector and given vector
     */
    public Vector add(int... coordinates) {
        Vector givenVector = new Vector(coordinates);
        return this.add(givenVector);
    }

    public Vector add(Vector anotherVector) {
        Map<Integer, Integer> newCoordinates = components.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<Integer, Integer> entry : anotherVector.getNonZeroComponents().entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            newCoordinates.put(key, value + this.getComponent(key));
        }

        return new Vector(newCoordinates);
    }

    /**
     * Produce the requested component of this vector
     * If index < 0, throws InvalidIndexException
     * @param index index of the component, >= 0
     * @return vector's component
     */
    public int getComponent(int index) {
        return components.getOrDefault(index, 0);
    }

    public Map<Integer, Integer> getNonZeroComponents() {
        return Collections.unmodifiableMap(components);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector vector = (Vector) o;

        return components.equals(vector.components);
    }

    @Override
    public int hashCode() {
        return components.hashCode();
    }
}
