package server;

import exceptions.InvalidDensityException;
import exceptions.InvalidDimensionException;
import org.junit.Before;
import org.junit.Test;
import server.application.WorldGenerator;
import server.application.WorldManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        WorldManager.getInstance().clear();
    }

    @Test
    public void testGenerationNoCells() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0,0,0,0);
        assertTrue(WorldManager.getInstance().getCells().isEmpty());

        WorldGenerator.generate(0.3,0,0,0);
        assertTrue(WorldManager.getInstance().getCells().isEmpty());

        WorldGenerator.generate(0.3,5,0,0);
        assertTrue(WorldManager.getInstance().getCells().isEmpty());

        WorldGenerator.generate(0.4,0,4,0);
        assertTrue(WorldManager.getInstance().getCells().isEmpty());

        WorldGenerator.generate(0.7,0,0,5);
        assertTrue(WorldManager.getInstance().getCells().isEmpty());

        WorldGenerator.generate(0.7,0,3,5);
        assertTrue(WorldManager.getInstance().getCells().isEmpty());

        WorldGenerator.generate(0.7,3,0,5);
        assertTrue(WorldManager.getInstance().getCells().isEmpty());

        WorldGenerator.generate(0.7,90,80,0);
        assertTrue(WorldManager.getInstance().getCells().isEmpty());
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
    public void testGenerateOneDimensionX() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.5,100,1,1);
        assertEquals(WorldManager.getInstance().getCells().size(), 50);
    }

    @Test
    public void testGenerateOneDimensionY() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.77,1,100,1);
        assertEquals(WorldManager.getInstance().getCells().size(), 77);
    }

    @Test
    public void testGenerateOneDimensionZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.911,1,1,100);
        assertEquals(WorldManager.getInstance().getCells().size(), 91);
    }

    @Test
    public void testGenerateTwoDimensionsXY() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.915,10,10,1);
        assertEquals(WorldManager.getInstance().getCells().size(), 91);
    }

    @Test
    public void testGenerateTwoDimensionsXZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.919,10,1,10);
        assertEquals(WorldManager.getInstance().getCells().size(), 91);
    }

    @Test
    public void testGenerateTwoDimensionsYZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.01,1,10,10);
        assertEquals(WorldManager.getInstance().getCells().size(), 1);
    }

    @Test
    public void testGenerateThreeDimensionsXYZ() throws InvalidDensityException, InvalidDimensionException {
        WorldGenerator.generate(0.9321,100,200,300);
        assertEquals(WorldManager.getInstance().getCells().size(), (int) (0.9321 * 100 * 200 * 300));
    }
}
