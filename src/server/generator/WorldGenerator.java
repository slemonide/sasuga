package server.generator;

import server.exceptions.InvalidDensityException;
import server.exceptions.InvalidDimensionException;
import server.model.Cell;
import server.model.Position;
import server.model.World;

import java.util.Random;

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

        if (x * y * z == 0) {
            return;
        }

        Random random = new Random();

        for (int x0 = 0; x0 <= x; x0++) {
            for (int y0 = 0; y0 <= y; y0++) {
                for (int z0 = 0; z0 <= z; z0++) {
                    if (random.nextInt(x * y * z) <= (x * y * z) * cellDensity) {
                        World.getInstance().add(new Cell(new Position(
                                x0 - x/2,
                                y0 - y/2,
                                z0 - z/2)));
                    }
                }
            }
        }
    }
}
