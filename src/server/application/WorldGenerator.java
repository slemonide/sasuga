package server.application;

import exceptions.InvalidDensityException;
import exceptions.InvalidDimensionException;

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
     *     number of cells to be generated = (int) (cellDensity * x * y * z)
     *     i.e. the number is rounded down
     * </p>
     *
     * @param cellDensity number of cells / std. cubic length
     * @param x x dimension of the cell blob
     * @param y y dimension of the cell blob
     * @param z z dimension of the cell blob
     * @throws InvalidDensityException if cellDensity is not between 0 and 1 (inclusive)
     * @throws InvalidDimensionException if x, y, or z is negative
     */
    public static void generate(double cellDensity, int x, int y, int z)
            throws InvalidDensityException, InvalidDimensionException {

    }
}
