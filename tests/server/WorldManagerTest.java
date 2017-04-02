package server;

import org.junit.Before;
import org.junit.Test;
import server.application.Cell;
import server.application.Vector3;
import server.application.WorldManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testSimpleDie() throws InterruptedException {
        WorldManager.getInstance().add(new Cell(new Vector3(0, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 1);
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector3(0, 0, 0))));
        Thread thread = new Thread(WorldManager.getInstance());
        thread.start();
        Thread.sleep(1000);
        assertEquals(0, WorldManager.getInstance().getCells().size());
        thread.interrupt();
    }

    @Test
    public void testHarderDie() throws InterruptedException {
        WorldManager.getInstance().add(new Cell(new Vector3(0, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector3(0, 0, 1)));
        WorldManager.getInstance().add(new Cell(new Vector3(0, 10, 0)));
        WorldManager.getInstance().add(new Cell(new Vector3(0, 10, 1)));
        assertEquals(WorldManager.getInstance().getCells().size(), 4);
        Thread thread = new Thread(WorldManager.getInstance());
        thread.start();
        Thread.sleep(1000);
        assertEquals(WorldManager.getInstance().getCells().size(), 0);
        thread.interrupt();
    }

    @Test
    public void testAnotherDie() throws InterruptedException {
        WorldManager.getInstance().add(new Cell(new Vector3(-1, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector3(0, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector3(1, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 3);
        //Thread thread = new Thread(WorldManager.getInstance());
        //thread.start();
        //Thread.sleep(1000);
        WorldManager.getInstance().tick();
        assertEquals(0, WorldManager.getInstance().getCells().size());
        //thread.interrupt();
    }

    @Test
    public void testGrow() {
        WorldManager.getInstance().add(new Cell(new Vector3(0, 0, -1)));
        WorldManager.getInstance().add(new Cell(new Vector3(0, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Vector3(0, 0, 1)));
        WorldManager.getInstance().tick();
        assertEquals(9, WorldManager.getInstance().getCells().size());
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector3(0, 0, 0))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector3(1, 0, 0))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector3(-1, 0, 0))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector3(0, 1, 0))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector3(0, -1, 0))));
    }
}
