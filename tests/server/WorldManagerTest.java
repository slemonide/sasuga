package server;

import com.jme3.math.Vector3f;
import org.junit.Before;
import org.junit.Test;
import server.application.Cell;
import server.application.WorldManager;
import static org.junit.Assert.*;

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
        WorldManager.getInstance().add(new Cell(new Vector3f(0, 0, 0)));
        assertEquals(WorldManager.getInstance().getCells().size(), 1);
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Vector3f(0, 0, 0))));
        WorldManager.getInstance().run();
        assertEquals(WorldManager.getInstance().getCells().size(), 0);
    }
}
