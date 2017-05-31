package geometry.parallelepipedSpace;

import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static geometry.parallelepipedSpace.Helpers.*;
import static org.junit.Assert.*;

/**
 * Tests the remove(Parallelepiped) method
 */
public class RemoveParallelepipedTest {
    private final ParallelepipedSpace testSpace = new ParallelepipedSpace();

    /**
     * Randomly-generated corner
     */
    private Position corner;

    // To be configured individually by each test
    private Parallelepiped parallelepipedA;
    private Parallelepiped parallelepipedB;

    @Before
    public void runBefore() {
        corner = getRandomPosition();

        testSpace.clear();
    }

    // -----------------------------------------------------------------------
    // Helper functions

    // -----------------------------------------------------------------------
    // Test properties

    /**
     * Tests that removing positions constituting parallelepiped is equivalent
     * to removing the actual parallelepiped
     *
     * <p>
     *     Parallelepipeds are chosen completely at random
     * </p>
     *
     * @see #parallelepipedRemovePositionsEquivalenceConcentrated()
     */
    @Test
    public void parallelepipedRemovePositionsEquivalence() {
        for (int i = 0; i < MAX_RANDOM_ITERATIONS_HARD; i++) {
            parallelepipedA = getRandomParallelepiped(RANDOM_REMOVE_MAX_EDGE);
            parallelepipedB = getRandomParallelepiped(RANDOM_REMOVE_MAX_EDGE);

            testSpace.add(parallelepipedA);
            ParallelepipedSpace parallelepipedSpaceCopy = new ParallelepipedSpace(testSpace);

            testSpace.remove(parallelepipedB);
            parallelepipedB.positions().forEach(parallelepipedSpaceCopy::remove);

            assertEquals(testSpace, parallelepipedSpaceCopy);
        }
    }

    /**
     * Tests that removing positions constituting parallelepiped is equivalent
     * to removing the actual parallelepiped
     *
     * <p>
     *     Tries to pick parallelepipeds in close proximity
     * </p>
     *
     * @see #parallelepipedRemovePositionsEquivalence()
     */
    @Test
    public void parallelepipedRemovePositionsEquivalenceConcentrated() {
        for (int i = 0; i < MAX_RANDOM_ITERATIONS_HARD; i++) {
            parallelepipedA = getRandomParallelepiped(RANDOM_REMOVE_MAX_EDGE);
            parallelepipedB = getRandomParallelepiped(parallelepipedA.getCorner(), RANDOM_REMOVE_MAX_EDGE);

            testSpace.add(parallelepipedA);
            ParallelepipedSpace parallelepipedSpaceCopy = new ParallelepipedSpace(testSpace);

            testSpace.remove(parallelepipedB);
            parallelepipedB.positions().forEach(parallelepipedSpaceCopy::remove);

            assertEquals(testSpace, parallelepipedSpaceCopy);
        }
    }

    @Test
    public void randomCornerIntersect() {
        for (int i = 0; i < MAX_RANDOM_ITERATIONS_HARD; i++) {
            parallelepipedA = getRandomParallelepiped();
            parallelepipedB = getRandomParallelepiped();

            Position maxShift = parallelepipedB.getSides().min(parallelepipedA.getSides());
            Position randomShift = getRandomPosition(maxShift);

            parallelepipedB = parallelepipedB.setCorner(parallelepipedA.getCorner()
                    .add(parallelepipedA.getSides()).subtract(randomShift));

            testSpace.add(parallelepipedA);

            assertEquals(1, testSpace.size());
            assertTrue(testSpace.contains(parallelepipedA.getCorner()));
            assertTrue(testSpace.contains(parallelepipedB.getCorner()));

            testSpace.remove(parallelepipedB);

            assertEquals(7, testSpace.size());
            assertTrue(testSpace.contains(parallelepipedA.getCorner()));
            assertFalse(testSpace.contains(parallelepipedB.getCorner()));
        }
    }

    /**
     * Tests that all positions outside the chosen parallelepiped are not removed
     */
    @Test
    public void allPositionsOutsideAreNotRemoved() {
        Set<Position> addedPositions = new HashSet<>();

        for (int i = 0; i < MAX_ENTITIES; i++) {
            Position randomPosition = getRandomPosition();

            addedPositions.add(randomPosition);
            testSpace.add(randomPosition);
        }

        for (int i = 0; i < MAX_RANDOM_ITERATIONS_HARD; i++) {
            Parallelepiped randomParallelepiped = getRandomParallelepiped();

            Set<Position> toBeRemoved = addedPositions.parallelStream()
                    .filter(randomParallelepiped::contains)
                    .collect(Collectors.toSet());

            addedPositions.removeAll(toBeRemoved);
            testSpace.remove(randomParallelepiped);

            toBeRemoved.forEach(position -> assertFalse(testSpace.contains(position)));
            addedPositions.forEach(position -> assertTrue(testSpace.contains(position)));
        }
    }

    /**
     * Tests that all positions outside the chosen parallelepiped are not removed
     */
    @Test
    public void allPositionsInsideAreRemoved() {
        Set<Position> addedPositions = new HashSet<>();

        for (int i = 0; i < MAX_ENTITIES; i++) {
            Position randomPosition = getRandomPosition(BOUND);

            addedPositions.add(randomPosition);
            testSpace.add(randomPosition);
        }

        for (int i = 0; i < MAX_RANDOM_ITERATIONS_HARD; i++) {
            Parallelepiped randomParallelepiped = getRandomParallelepiped(getRandomPosition(BOUND));

            Set<Position> toBeRemoved = addedPositions.parallelStream()
                    .filter(randomParallelepiped::contains)
                    .collect(Collectors.toSet());

            addedPositions.removeAll(toBeRemoved);
            testSpace.remove(randomParallelepiped);

            toBeRemoved.forEach(position -> assertFalse(testSpace.contains(position)));
            addedPositions.forEach(position -> assertTrue(testSpace.contains(position)));
        }
    }

    /**
     * Tests that removing sub-parallelepipeds constituting
     * parallelepiped is equivalent to removing the actual parallelepiped
     */
    @Test
    public void parallelepipedRemoveSubsEquivalence() {
        // TODO: finish
    }

    // -----------------------------------------------------------------------
    // Concrete tests

    // -----------------------------------------------------------------------
    // Removing a unit parallelepiped is equivalent to removing a position
    // Copied from RebuildingLogicTest

    @Test
    public void testRemoveSliceThroughCube() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    testSpace.add(new Parallelepiped(new Position(x, y, z)));
                }
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                testSpace.remove(new Parallelepiped(new Position(x, 1, z)));
            }
        }

        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                3, 1, 3)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,2,0),
                3, 1, 3)));
    }

    @Test
    public void testRemoveSliceThroughBigParallelepiped() {
        buildParallelepiped(testSpace);
        for (int x = 0; x < Helpers.MAX_POSITIONS_X; x++) {
            for (int y = 0; y < Helpers.MAX_POSITIONS_Y; y++) {
                testSpace.remove(new Parallelepiped(new Position(x, y, Helpers.MAX_POSITIONS_Z/2)));
            }
        }

        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0, 0, 0),
                Helpers.MAX_POSITIONS_X,
                Helpers.MAX_POSITIONS_Y,
                Helpers.MAX_POSITIONS_Z/2)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(
                0,
                0,
                Helpers.MAX_POSITIONS_Z/2 + 1),
                Helpers.MAX_POSITIONS_X,
                Helpers.MAX_POSITIONS_Y,
                Helpers.MAX_POSITIONS_Z/2)));
    }

    // Other tests
    @Test
    public void noIntersection() {
        parallelepipedA = new Parallelepiped(corner, 10, 20, 30);
        parallelepipedB = new Parallelepiped(corner.add(1000, -24124, 100), 31, 23, 12);

        testSpace.add(parallelepipedA);
        testSpace.remove(parallelepipedB);

        assertEquals(1, testSpace.size());
        assertEquals(parallelepipedA.volume(), testSpace.getVolume());
        assertTrue(testSpace.contains(parallelepipedA));
        assertTrue(testSpace.getParallelepipeds().contains(parallelepipedA));
    }

    @Test
    public void sameOne() {
        parallelepipedA = getRandomParallelepiped(Integer.MAX_VALUE);

        parallelepipedB = parallelepipedA;

        testSpace.add(parallelepipedA);
        testSpace.remove(parallelepipedB);

        assertTrue(testSpace.isEmpty());
        assertEquals(0, testSpace.size());
        assertEquals(0, testSpace.getVolume());
        assertTrue(testSpace.getParallelepipeds().isEmpty());
    }

    @Test
    public void testCornerIntersect() {
        parallelepipedA = new Parallelepiped(corner, 10, 20, 30);
        parallelepipedB = new Parallelepiped(corner.add(parallelepipedA.getSides()), 20, 40, 50);

        testSpace.add(parallelepipedA);
        testSpace.remove(parallelepipedB);

        assertEquals(7, testSpace.size());

        assertTrue(testSpace.contains(parallelepipedA.setSides(parallelepipedA.getSides().subtractOne())));

        assertTrue(testSpace.contains(new Parallelepiped(corner.add(10, 0, 0), 1, 19, 29)));
        assertTrue(testSpace.contains(new Parallelepiped(corner.add(0, 20, 0), 9, 1, 29)));
        assertTrue(testSpace.contains(new Parallelepiped(corner.add(0, 0, 30), 9, 19, 1)));

        assertTrue(testSpace.contains(new Parallelepiped(corner.add(10, 20, 0), 1, 1, 29)));
        assertTrue(testSpace.contains(new Parallelepiped(corner.add(10, 0, 30), 1, 19, 1)));
        assertTrue(testSpace.contains(new Parallelepiped(corner.add(0, 20, 30), 9, 1, 1)));
    }
}
