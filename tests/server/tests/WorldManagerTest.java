package server.tests;

import org.junit.Before;
import org.junit.Test;
import server.model.Cell;
import server.model.Vector;
import server.model.WorldManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Test for WorldManager
 */
public class WorldManagerTest {
    @Before
    public void runBefore() {
        WorldManager.getInstance().clear();
    }

    @Test
    public void testConstructor() {
        assertEquals(WorldManager.getInstance().getCells().size(), 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCellsUnmodifiable() {
        WorldManager.getInstance().getCells().clear();
    }

    @Test
    public void testRemoveCell() {
        WorldManager.getInstance().add(new Cell(new Vector(0, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 1);


        WorldManager.getInstance().remove(new Cell(new Vector(0, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 0);
    }

    @Test
    public void testTickNoCells() {
        WorldManager.getInstance().tick();
        assertTrue(WorldManager.getInstance().getCells().isEmpty());
    }

    @Test
    public void testSimpleDie() throws InterruptedException {
        WorldManager.getInstance().add(new Cell(new Vector(0, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 1);
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector(0, 0, 0))));
        Thread thread = new Thread(WorldManager.getInstance());
        thread.start();
        Thread.sleep(1000);
        assertEquals(0, WorldManager.getInstance().getCells().size());
        thread.interrupt();
    }

    @Test
    public void testHarderDie() throws InterruptedException {
        WorldManager.getInstance().add(new Cell(new Vector(0, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector(0, 0, 1)));
        WorldManager.getInstance().add(new Cell(new Vector(0, 10, 0)));
        WorldManager.getInstance().add(new Cell(new Vector(0, 10, 1)));
        assertEquals(WorldManager.getInstance().getCells().size(), 4);
        Thread thread = new Thread(WorldManager.getInstance());
        thread.start();
        Thread.sleep(1000);
        assertEquals(WorldManager.getInstance().getCells().size(), 0);
        thread.interrupt();
    }

    @Test
    public void testDieTwoCellsOneCellAPart() {
        WorldManager.getInstance().add(new Cell(new Vector(-1, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector(1, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 2);
        WorldManager.getInstance().tick();
        assertEquals(0, WorldManager.getInstance().getCells().size());
    }

    @Test
    public void testDieTwoCellsOneCellAPartThread() throws InterruptedException {
        WorldManager.getInstance().add(new Cell(new Vector(-1, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector(1, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 2);
        Thread thread = new Thread(WorldManager.getInstance());
        thread.start();
        Thread.sleep(1000);
        assertEquals(0, WorldManager.getInstance().getCells().size());
        thread.interrupt();
    }

    @Test
    public void testDieTwoCellsFarApart() {
        WorldManager.getInstance().add(new Cell(new Vector(-2, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector(2, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 2);
        Thread thread = new Thread(WorldManager.getInstance());
        WorldManager.getInstance().tick();
        assertEquals(0, WorldManager.getInstance().getCells().size());
    }

    @Test
    public void testDieTwoCellsTwoApart() {
        WorldManager.getInstance().add(new Cell(new Vector(0, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector(3, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 2);
        Thread thread = new Thread(WorldManager.getInstance());
        WorldManager.getInstance().tick();
        assertEquals(0, WorldManager.getInstance().getCells().size());
    }

    @Test
    public void testGrow() {
        WorldManager.getInstance().add(new Cell(new Vector(0, 0, -1)));
        WorldManager.getInstance().add(new Cell(new Vector(0, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector(0, 0, 1)));
        WorldManager.getInstance().tick();
        assertEquals(9, WorldManager.getInstance().getCells().size());
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector(0, 0, 0))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector(1, 0, 0))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector(-1, 0, 0))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector(0, 1, 0))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector(0, -1, 0))));
    }
}
