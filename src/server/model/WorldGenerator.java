package server.model;

import exceptions.InvalidDensityException;
import exceptions.InvalidDimensionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Generates world
 */
public class WorldGenerator {
    /**
     * Generates a world with the given cell density and dimensions
     * <p>
     *     Cells are generated inside a parallelepiped of the provided dimensions, centered at the origin
     * </p>
     * <p>
     *     number of cells to be generated = (int) (cellDensity * x * y * z)
     *     i.e. the number is rounded down
     * </p>
     *
     * @param cellDensity number of cells / std. cubic length
     * @param x x dimension of the cell blob
     * @param y y dimension of the cell blob
     * @param z z dimension of the cell blob
     * @throws InvalidDensityException if cellDensity is not between 0 and 0.5 (inclusive)
     * @throws InvalidDimensionException if x, y, or z is negative
     */
    public static void generate(double cellDensity, int x, int y, int z)
            throws InvalidDensityException, InvalidDimensionException {
        if (cellDensity > 1 || cellDensity < 0) {
            throw new InvalidDensityException();
        } else if (x < 0 || y < 0 || z < 0) {
            throw new InvalidDimensionException();
        }

        int numberOfCellsToBeAdded = (int) (cellDensity * x * y * z);

        List<Vector3> possiblePositions = new ArrayList<Vector3>();

        int j = 0;
        while (j < x * y * z) {
            int x0 = j % x - x/2;
            int y0 = (j / x) % y - y/2;
            int z0 = j / (x * y) - z/2;

            possiblePositions.add(new Vector3(x0, y0, z0));
            j++;
        }
        /*
        for (int x0 = x - x/2; x0 <= x/2; x0++) {
            for (int y0 = y - y/2; y0 <= y/2; y0++) {
                for (int z0 = z - z/2; z <= z/2; z0++) {
                    possiblePositions.add(new Vector3(x0, y0, z0));
                }
            }
        }
        */

        Collections.shuffle(possiblePositions);

        for (int i = 0; i < numberOfCellsToBeAdded; i++) {
            WorldManager.getInstance().add(new Cell(possiblePositions.get(i)));
        }
    }
}
