package model;

import model.RandomWalkCell;
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

    @Test
    public void testRemoveCellPosition() {
        World.getInstance().add(new Cell(new Position(0, 0, 0)));
        assertEquals(World.getInstance().getCells().size(), 1);
        assertEquals(World.getInstance().getPopulationSize(), 1);
        assertEquals(World.getInstance().getGeneration(), 1);
        assertEquals(World.getInstance().getTickTime(), 0, DELTA);

        World.getInstance().remove(new Position(0, 0, 0));
        assertEquals(World.getInstance().getCells().size(), 0);
        assertEquals(World.getInstance().getPopulationSize(), 0);
        assertEquals(World.getInstance().getGeneration(), 2);
        assertEquals(World.getInstance().getTickTime(), 0, DELTA);
    }

    @Test
    public void testGrowthRate() {
        World.getInstance().add(new Cell(new Position(0,0,0)));
        World.getInstance().start();
        assertEquals(0, World.getInstance().getGrowthRate());

        World.getInstance().interrupt();
        World.getInstance().reset();

        World.getInstance().add(new RandomWalkCell(new Position(0,0,0)));
        World.getInstance().tick();
        assertEquals(1, World.getInstance().getGrowthRate());

        World.getInstance().reset();

        World.getInstance().add(new RandomWalkCell(new Position(0, 0, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 10, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 20, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 30, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 40, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 50, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 60, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 70, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 80, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 90, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 100, 0)));
        World.getInstance().tick();
        assertEquals(11, World.getInstance().getGrowthRate());
    }

    @Test
    public void testRun() throws InterruptedException {
        World.getInstance().add(new RandomWalkCell(new Position(0, 0, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 10, 0)));
        World.getInstance().add(new RandomWalkCell(new Position(0, 20, 0)));

        Thread worldThread = new Thread(World.getInstance());
        worldThread.start();
        Thread.sleep(100);
        worldThread.interrupt();
        assertTrue(World.getInstance().getGeneration() > 1);
        assertTrue(World.getInstance().getPopulationSize() > 3);
    }
}
