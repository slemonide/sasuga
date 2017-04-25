package tests;

import org.junit.Before;
import org.junit.Test;
import model.Cell;
import model.Position;
import model.World;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Test for World
 */
public class WorldTest {
    private static final double DELTA = 0.01;

    @Before
    public void runBefore() {
        World.getInstance().reset();
    }

    @Test
    public void testConstructor() {
        assertTrue(World.getInstance().getCells().isEmpty());
        assertEquals(World.getInstance().getCells().size(), 0);
        assertEquals(World.getInstance().getGeneration(), 0);
        assertEquals(World.getInstance().getPopulationSize(), 0);
        assertEquals(World.getInstance().getTickTime(), 0, DELTA);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCellsUnmodifiable() {
        World.getInstance().getCells().clear();
    }

    @Test
    public void testRemoveCell() {
        World.getInstance().add(new Cell(new Position(0, 0, 0)));
        assertEquals(World.getInstance().getCells().size(), 1);
        assertEquals(World.getInstance().getPopulationSize(), 1);
        assertEquals(World.getInstance().getGeneration(), 1);
        assertEquals(World.getInstance().getTickTime(), 0, DELTA);

        World.getInstance().remove(new Cell(new Position(0, 0, 0)));
        assertEquals(World.getInstance().getCells().size(), 0);
        assertEquals(World.getInstance().getPopulationSize(), 0);
        assertEquals(World.getInstance().getGeneration(), 2);
        assertEquals(World.getInstance().getTickTime(), 0, DELTA);
    }
}
