package tests.server;

import org.junit.Before;
import org.junit.Test;
import server.model.Cell;
import server.model.Position;
import server.model.World;
import server.rules.NeighbourhoodCellular;

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
public class GameOfLIfeActiveRuleTest {
    private static final int MAX_TICKS = 100;
    private static final Position ORIGIN = new Position(0, 0, 0);

    @Before
    public void runBefore() {
        World.getInstance().reset();

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

        //World.getInstance().setRule(gameOfLife);
    }

    @Test
    public void testTickBasic() {
        for (int i = 1; i < MAX_TICKS; i++) {
            World.getInstance().tick();
            assertEquals(i, World.getInstance().getGeneration());
            assertEquals(0, World.getInstance().getPopulationSize());
        }
    }

    @Test
    public void testTickDieInOneTick() {
        World.getInstance().add(new Cell(ORIGIN));
        assertEquals(0, World.getInstance().getGeneration());
        assertEquals(1, World.getInstance().getPopulationSize());
        World.getInstance().tick();
        assertFalse(World.getInstance().getCells().contains(new Cell(ORIGIN)));
        assertEquals(1, World.getInstance().getGeneration());
        assertEquals(0, World.getInstance().getPopulationSize());
    }

    @Test
    public void testTickSimpleOscillator() {
        World.getInstance().add(new Cell(new Position(0, 0, -1)));
        World.getInstance().add(new Cell(new Position(0, 0, 0)));
        World.getInstance().add(new Cell(new Position(0, 0, 1)));
        /*
         * 0 1 0
         * 0 1 0
         * 0 1 0
         */

        for (int i = 0; i < MAX_TICKS; i++) {
            World.getInstance().tick();

            if (i % 2 == 0) {
                /*
                 * 0 0 0
                 * 1 1 1
                 * 0 0 0
                 */
                assertTrue(World.getInstance().getCells().contains(new Cell(new Position(0, 0, 0))));
                assertTrue(World.getInstance().getCells().contains(new Cell(new Position(1, 0, 0))));
                assertTrue(World.getInstance().getCells().contains(new Cell(new Position(-1, 0, 0))));
            } else {
                /*
                 * 0 1 0
                 * 0 1 0
                 * 0 1 0
                 */
                assertTrue(World.getInstance().getCells().contains(new Cell(new Position(0, 0, -1))));
                assertTrue(World.getInstance().getCells().contains(new Cell(new Position(0, 0, 0))));
                assertTrue(World.getInstance().getCells().contains(new Cell(new Position(0, 0, 1))));
            }
            assertEquals(3, World.getInstance().getPopulationSize());
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
        World.getInstance().add(new Cell(new Position(1, 0,1)));
        World.getInstance().add(new Cell(new Position(2, 0,1)));
        World.getInstance().add(new Cell(new Position(1, 0,2)));
        World.getInstance().add(new Cell(new Position(2, 0,2)));

        // the other thing
        World.getInstance().add(new Cell(new Position(5, 0,1)));
        World.getInstance().add(new Cell(new Position(5, 0,2)));
        World.getInstance().add(new Cell(new Position(6, 0,1)));
        World.getInstance().add(new Cell(new Position(6, 0,3)));
        World.getInstance().add(new Cell(new Position(7, 0,2)));

        assertEquals(9, World.getInstance().getPopulationSize());
        // the square
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(1, 0,1))));
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(2, 0,1))));
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(1, 0,2))));
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(2, 0,2))));

        // the other thing
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(5, 0,1))));
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(5, 0,2))));
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(6, 0,1))));
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(6, 0,3))));
        assertTrue(World.getInstance().getCells().contains(new Cell(new Position(7, 0,2))));

        for (int i = 0; i < MAX_TICKS; i++) {
            World.getInstance().tick();
            assertEquals(9, World.getInstance().getPopulationSize());
            // the square
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(1, 0,1))));
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(2, 0,1))));
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(1, 0,2))));
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(2, 0,2))));

            // the other thing
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(5, 0,1))));
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(5, 0,2))));
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(6, 0,1))));
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(6, 0,3))));
            assertTrue(World.getInstance().getCells().contains(new Cell(new Position(7, 0,2))));
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
        World.getInstance().add(new Cell(new Position(1, 0,2)));
        World.getInstance().add(new Cell(new Position(2, 0,2)));
        World.getInstance().add(new Cell(new Position(2, 0,3)));

        // other thing
        World.getInstance().add(new Cell(new Position(7, 0,1)));
        World.getInstance().add(new Cell(new Position(6, 0,3)));
        World.getInstance().add(new Cell(new Position(7, 0,3)));
        World.getInstance().add(new Cell(new Position(8, 0,3)));


        for (int i = 1; i < 130; i++) {
            World.getInstance().tick();
            assertNotEquals(0, World.getInstance().getPopulationSize());
        }

        World.getInstance().tick();
        assertEquals(0, World.getInstance().getPopulationSize());
    }
}
