package tests.server;

import org.junit.Before;
import org.junit.Test;
import server.model.Cell;
import server.model.Position;
import server.model.WorldManager;
import server.rulesets.NeighbourhoodCellular;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Test that the system works fine if configured with the Conway's Game Of Life rules
 */
public class GameOfLIfeRuleTest {
    private static final int MAX_TICKS = 100;
    private static final Position ORIGIN = new Position(0, 0, 0);

    @Before
    public void runBefore() {
        WorldManager.getInstance().reset();

        NeighbourhoodCellular gameOfLife = new NeighbourhoodCellular();

        gameOfLife.setLowerBound(1);
        gameOfLife.setUpperBound(3);

        Set<Position> neighbourhood = new HashSet<>();

        neighbourhood.add(new Position(1,0,-1));
        neighbourhood.add(new Position(1,0,0));
        neighbourhood.add(new Position(1,0,1));

        neighbourhood.add(new Position(0,0,-1));
        //neighbourhood.add(new Position(0,0,0));
        neighbourhood.add(new Position(0,0,1));

        neighbourhood.add(new Position(-1,0,-1));
        neighbourhood.add(new Position(-1,0,0));
        neighbourhood.add(new Position(-1,0,1));

        gameOfLife.setNeighbourhood(neighbourhood);

        //WorldManager.getInstance().setRule(gameOfLife);
    }

    @Test
    public void testTickBasic() {
        for (int i = 1; i < MAX_TICKS; i++) {
            WorldManager.getInstance().tick();
            assertEquals(i, WorldManager.getInstance().getGeneration());
            assertEquals(0, WorldManager.getInstance().getPopulationSize());
        }
    }

    @Test
    public void testTickDieInOneTick() {
        WorldManager.getInstance().add(new Cell(ORIGIN));
        assertEquals(0, WorldManager.getInstance().getGeneration());
        assertEquals(1, WorldManager.getInstance().getPopulationSize());
        WorldManager.getInstance().tick();
        assertFalse(WorldManager.getInstance().getCells().contains(new Cell(ORIGIN)));
        assertEquals(1, WorldManager.getInstance().getGeneration());
        assertEquals(0, WorldManager.getInstance().getPopulationSize());
    }

    @Test
    public void testTickSimpleOscillator() {
        WorldManager.getInstance().add(new Cell(new Position(0, 0, -1)));
        WorldManager.getInstance().add(new Cell(new Position(0, 0, 0)));
        WorldManager.getInstance().add(new Cell(new Position(0, 0, 1)));
        /*
         * 0 1 0
         * 0 1 0
         * 0 1 0
         */

        for (int i = 0; i < MAX_TICKS; i++) {
            WorldManager.getInstance().tick();

            if (i % 2 == 0) {
                /*
                 * 0 0 0
                 * 1 1 1
                 * 0 0 0
                 */
                assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(0, 0, 0))));
                assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(1, 0, 0))));
                assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(-1, 0, 0))));
            } else {
                /*
                 * 0 1 0
                 * 0 1 0
                 * 0 1 0
                 */
                assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(0, 0, -1))));
                assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(0, 0, 0))));
                assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(0, 0, 1))));
            }
            assertEquals(3, WorldManager.getInstance().getPopulationSize());
        }
    }

    @Test
    public void testTickStableStructures() {
        /*   | 0 1 2 3 4 5 6 7 8
         *---|------------------
         * 1 | 0 1 1 0 0 1 1 0 0
         * 2 | 0 1 1 0 0 1 0 1 0
         * 3 | 0 0 0 0 0 0 1 0 0
         */
        // the square
        WorldManager.getInstance().add(new Cell(new Position(1, 0,1)));
        WorldManager.getInstance().add(new Cell(new Position(2, 0,1)));
        WorldManager.getInstance().add(new Cell(new Position(1, 0,2)));
        WorldManager.getInstance().add(new Cell(new Position(2, 0,2)));

        // the other thing
        WorldManager.getInstance().add(new Cell(new Position(5, 0,1)));
        WorldManager.getInstance().add(new Cell(new Position(5, 0,2)));
        WorldManager.getInstance().add(new Cell(new Position(6, 0,1)));
        WorldManager.getInstance().add(new Cell(new Position(6, 0,3)));
        WorldManager.getInstance().add(new Cell(new Position(7, 0,2)));

        assertEquals(9, WorldManager.getInstance().getPopulationSize());
        // the square
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(1, 0,1))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(2, 0,1))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(1, 0,2))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(2, 0,2))));

        // the other thing
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(5, 0,1))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(5, 0,2))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(6, 0,1))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(6, 0,3))));
        assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(7, 0,2))));

        for (int i = 0; i < MAX_TICKS; i++) {
            WorldManager.getInstance().tick();
            assertEquals(9, WorldManager.getInstance().getPopulationSize());
            // the square
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(1, 0,1))));
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(2, 0,1))));
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(1, 0,2))));
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(2, 0,2))));

            // the other thing
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(5, 0,1))));
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(5, 0,2))));
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(6, 0,1))));
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(6, 0,3))));
            assertTrue(WorldManager.getInstance().getCells().contains(new Cell(new Position(7, 0,2))));
        }
    }

    @Test
    public void testTickDieHard() {
        /* Die Hard pattern:
         *   | 1 2 3 4 5 6 7 8
         *---|----------------
         * 1 | 0 0 0 0 0 0 1 0
         * 2 | 1 1 0 0 0 0 0 0
         * 3 | 0 1 0 0 0 1 1 1
         *
         * Should cease to exist in exactly 130 generations
         *
         */
        // the bottom left thing
        WorldManager.getInstance().add(new Cell(new Position(1, 0,2)));
        WorldManager.getInstance().add(new Cell(new Position(2, 0,2)));
        WorldManager.getInstance().add(new Cell(new Position(2, 0,3)));

        // other thing
        WorldManager.getInstance().add(new Cell(new Position(7, 0,1)));
        WorldManager.getInstance().add(new Cell(new Position(6, 0,3)));
        WorldManager.getInstance().add(new Cell(new Position(7, 0,3)));
        WorldManager.getInstance().add(new Cell(new Position(8, 0,3)));


        for (int i = 1; i < 130; i++) {
            WorldManager.getInstance().tick();
            assertNotEquals(0, WorldManager.getInstance().getPopulationSize());
        }

        WorldManager.getInstance().tick();
        assertEquals(0, WorldManager.getInstance().getPopulationSize());
    }
}
