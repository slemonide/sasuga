package tests.server;

import server.exceptions.InvalidDensityException;
import server.exceptions.InvalidDimensionException;
import org.junit.Before;
import org.junit.Test;
import server.model.Cell;
import server.model.WorldGenerator;
import server.model.WorldManager;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Test for WorldGenerator
 */
public class WorldGeneratorTest {
    @Before
    public void runBefore() {
        //WorldManager.getInstance().setRule(new RandomWalk());
        //WorldManager.getInstance().getRule().clear();
    }

    @Test
    public void testGenerationNoCells() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0,0,0,0);
        //assertTrue(WorldManager.getInstance().getRule().getCells().isEmpty());

        WorldGenerator.generate(0.3,0,0,0);
        //assertTrue(WorldManager.getInstance().getRule().getCells().isEmpty());

        WorldGenerator.generate(0.3,5,0,0);
        //assertTrue(WorldManager.getInstance().getRule().getCells().isEmpty());

        WorldGenerator.generate(0.4,0,4,0);
        //assertTrue(WorldManager.getInstance().getRule().getCells().isEmpty());

        WorldGenerator.generate(0.7,0,0,5);
        //assertTrue(WorldManager.getInstance().getRule().getCells().isEmpty());

        WorldGenerator.generate(0.7,0,3,5);
        //assertTrue(WorldManager.getInstance().getRule().getCells().isEmpty());

        WorldGenerator.generate(0.7,3,0,5);
        //assertTrue(WorldManager.getInstance().getRule().getCells().isEmpty());

        WorldGenerator.generate(0.7,90,80,0);
        //assertTrue(WorldManager.getInstance().getRule().getCells().isEmpty());
    }

    @Test(expected = InvalidDensityException.class)
    public void testInvalidDensityExceptionNegativeDensity() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(-0.1, 0, 0, 0);
        fail();
    }

    @Test(expected = InvalidDimensionException.class)
    public void testInvalidDimensionExceptionX() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.4, -1, 0, 0);
        fail();
    }

    @Test(expected = InvalidDimensionException.class)
    public void testInvalidDimensionExceptionY() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.1, 0, -2, 0);
        fail();
    }

    @Test(expected = InvalidDimensionException.class)
    public void testInvalidDimensionExceptionZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.7, 0, 0, -3);
        fail();
    }

    @Test(expected = InvalidDimensionException.class)
    public void testInvalidDimensionExceptionXY() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.4, -1, -3, 0);
        fail();
    }

    @Test(expected = InvalidDimensionException.class)
    public void testInvalidDimensionExceptionXZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.4, -1, 0, -3);
        fail();
    }

    @Test(expected = InvalidDimensionException.class)
    public void testInvalidDimensionExceptionYZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.4, 0, -1234, -215);
        fail();
    }

    @Test(expected = InvalidDimensionException.class)
    public void testInvalidDimensionExceptionXYZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.4, -1, -41, -123);
        fail();
    }

    @Test(expected = InvalidDensityException.class)
    public void testInvalidDensityExceptionLargeDensity() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(1.1, 0, 0, 0);
        fail();
    }

    @Test
    public void testGenerateOneDimensionXSmall() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.2,10,1,1);
        assertEquals(2, WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testGenerateOneDimensionX() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.5,100,1,1);
        assertEquals(50, WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testGenerateOneDimensionY() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.77,1,100,1);
        assertEquals(77, WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testGenerateOneDimensionZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.911,1,1,100);
        assertEquals(91, WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testGenerateTwoDimensionsXY() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.915,10,10,1);
        assertEquals(91, WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testGenerateTwoDimensionsXZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.919,10,1,10);
        assertEquals(91, WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testGenerateTwoDimensionsYZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.01,1,10,10);
        assertEquals(1, WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testGenerateThreeDimensionsXYZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.9321,100,50,150);
        assertEquals((int) (0.9321 * 100 * 50 * 150), WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testGenerateBoundaries() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.9321,100,50,150);
        int maxX, minX, maxY, minY, maxZ, minZ;
        maxX = minX = maxY = minY = maxZ = minZ = 0;

        Set<Cell> cells = WorldManager.getInstance().getCells();

        for (Cell cell : cells) {
            int x = cell.getPosition().getComponent(0);
            int y = cell.getPosition().getComponent(1);
            int z = cell.getPosition().getComponent(2);

            maxX = (x > maxX) ? x : maxX;
            maxY = (y > maxY) ? y : maxY;
            maxZ = (z > maxZ) ? z : maxZ;

            minX = (x < minX) ? x : minX;
            minY = (y < minY) ? y : minY;
            minZ = (z < minZ) ? z : minZ;
        }

        assertTrue(maxX <= 50);
        assertTrue(maxY <= 25);
        assertTrue(maxZ <= 175);

        assertTrue(minX >= -50);
        assertTrue(minY >= -25);
        assertTrue(minZ >= -175);
    }
}
