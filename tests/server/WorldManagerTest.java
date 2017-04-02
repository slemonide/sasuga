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
    public void testSimpleDie() {
        WorldManager.getInstance().add(new Cell(new Vector3(0, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 1);
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector3(0, 0, 0))));
        WorldManager.getInstance().run();
        assertEquals(WorldManager.getInstance().getCells().size(), 0);
    }
}
