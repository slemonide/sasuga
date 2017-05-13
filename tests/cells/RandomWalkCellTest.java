package cells;

import geometry.Position;
import world.World;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomWalkCellTest {
    private static final int MAX_CELLS = 1000;

    @Before
    public void runBefore() {
        World.getInstance().reset();

        World.getInstance().add(new RandomWalkCell(new Position(0,0,0)));
        World.getInstance().tick();
    }

    @Test
    public void testTickSimple() {
        assertEquals(1, World.getInstance().getCells().size());
        World.getInstance().tick();
        assertEquals(2, World.getInstance().getCells().size());
    }

    @Test
    public void testTickHard() {
        for (int i = 1; i < MAX_CELLS; i++) {
            assertTrue(World.getInstance().getCells().size() <= i);
            World.getInstance().tick();
        }
    }
}
