package geometry.parallelepipedSpace;

import geometry.Axis;
import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Tests the remove(Parallelepiped) method
 */
public class RemoveParallelepipedTest {
    private final ParallelepipedSpace testSpace = new ParallelepipedSpace();
    private final static Random RANDOM = new Random();

    // -----------------------------------------------------------------------
    // User-defined constants

    /**
     * Default number of iterations for random-pick tests
     */
    private static final int MAX_RANDOM_ITERATIONS = 10000;

    /**
     * Default number of iterations for computationally-demanding
     * random-pick tests
     */
    private static final int MAX_RANDOM_ITERATIONS_HARD = 100;

    /**
     * Specifies how many items to generate at maximum
     */
    private static final int MAX_ENTITIES = 100;


    /**
     * Specifies tha maximum edge used for the position-removing equivalence test
     */
    private static final int RANDOM_REMOVE_MAX_EDGE = 5;

    /**
     * Randomly-generated corner
     */
    private Position corner;

    // Constants for the tests that test helper functions
    /**
     * Default bound with which to search for a random number
     */
    private static final int BOUND = 1000;

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

    /**
     * Produces a random number in the interval (-bound, bound)
     * @param bound specifies the interval from which to chose the number
     * @return a random number in the interval (-bound, bound)
     */
    private int getRandomNumber(int bound) {
        return (RANDOM.nextInt(1) * 2 - 1) * RANDOM.nextInt(bound);
    }

    /**
     * Produces a random position without any upper or lower bound
     * @return a random position
     */
    @NotNull
    private static Position getRandomPosition() {
        return new Position(RANDOM.nextInt(), RANDOM.nextInt(), RANDOM.nextInt());
    }

    /**
     * Produces a random position with components in the interval (-bound, bound)
     * @param bound specifies the interval from which to chose the components
     * @return a random position
     */
    @NotNull
    private Position getRandomPosition(int bound) {
        return new Position(
                getRandomNumber(bound),
                getRandomNumber(bound),
                getRandomNumber(bound));
    }

    /**
     * Produces a random position in the set (-bound, bound)
     * @param bound specifies the set from which to chose the components
     * @return a random position
     */
    @NotNull
    private Position getRandomPosition(@NotNull Position bound) {
        return new Position(
                getRandomNumber(bound.x),
                getRandomNumber(bound.y),
                getRandomNumber(bound.z));
    }

    /**
     * Produces a random parallelepiped with the given maximum side length
     * @param maxSideLength maximum side length
     * @return a random parallelepiped with the given maximum side length
     */
    @NotNull
    private static Parallelepiped getRandomParallelepiped(int maxSideLength) {
        return new Parallelepiped(getRandomPosition(),
                RANDOM.nextInt(maxSideLength) + 1,
                RANDOM.nextInt(maxSideLength) + 1,
                RANDOM.nextInt(maxSideLength) + 1);
    }

    /**
     * Randomly pick any parallelepiped
     * @return unspecified parallelepiped
     */
    @NotNull
    private Parallelepiped getRandomParallelepiped() {
        return getRandomParallelepiped(Integer.MAX_VALUE);
    }

    /**
     * Produces a random parallelepiped with the specified corner
     * @param corner of the parallelepiped
     * @return a random parallelepiped with the given corner
     */
    @NotNull
    private Parallelepiped getRandomParallelepiped(@NotNull Position corner) {
        return new Parallelepiped(corner,
                RANDOM.nextInt(Integer.MAX_VALUE) + 1,
                RANDOM.nextInt(Integer.MAX_VALUE) + 1,
                RANDOM.nextInt(Integer.MAX_VALUE) + 1);
    }

    /**
     * Produces a random parallelepiped close to the given position
     *
     * <p>
     *     center of the returned parallelepiped is no further than maxSideLength
     * </p>
     * @param center position near which to pick a parallelepiped
     * @param maxSideLength maximum side length
     * @return a random parallelepiped near the given center
     */
    @NotNull
    private Parallelepiped getRandomParallelepiped(@NotNull Position center, int maxSideLength) {
        return new Parallelepiped(center.add(getRandomPosition(maxSideLength)),
                RANDOM.nextInt(maxSideLength) + 1,
                RANDOM.nextInt(maxSideLength) + 1,
                RANDOM.nextInt(maxSideLength) + 1);
    }

    // -----------------------------------------------------------------------
    // Test helper functions
    @Test
    public void testGetRandomNumber() {
        int count = 0;
        int sum = 0;

        for (int i = 0; i < MAX_RANDOM_ITERATIONS; i++) {
            int randomNumber = getRandomNumber(BOUND);
            assertTrue(randomNumber > -BOUND);
            assertTrue(randomNumber <  BOUND);

            sum += randomNumber;
            count++;
        }

        assertNotEquals(getRandomNumber(BOUND), sum / count);
    }

    @Test
    public void testGetRandomPosition() {
        int count = 0;
        Position sum = new Position();

        for (int i = 0; i < MAX_RANDOM_ITERATIONS; i++) {
            Position randomPosition = getRandomPosition();

            sum = sum.add(randomPosition);
            count++;
        }

        assertNotEquals(getRandomPosition(), sum.divide(count));
    }

    @Test
    public void testGetRandomPositionBound() {
        int count = 0;
        Position sum = new Position();

        for (int i = 0; i < MAX_RANDOM_ITERATIONS; i++) {
            Position randomPosition = getRandomPosition(BOUND);
            for (Axis axis : Axis.values()) {
                assertTrue(randomPosition.get(axis) > -BOUND);
                assertTrue(randomPosition.get(axis) < BOUND);
            }

            sum = sum.add(randomPosition);
            count++;
        }

        assertNotEquals(getRandomPosition(BOUND), sum.divide(count));
    }

    @Test
    public void testGetRandomParallelepipedBound() {
        for (int i = 0; i < MAX_RANDOM_ITERATIONS; i++) {
            Parallelepiped randomParallelepiped = getRandomParallelepiped(corner, BOUND);

            Position randomCenter = randomParallelepiped.center();
            Position randomDisplacement = randomCenter.subtract(corner);
            for (Axis axis : Axis.values()) {
                assertTrue(randomDisplacement.get(axis) > -BOUND);
                assertTrue(randomDisplacement.get(axis) < BOUND);
            }
        }
    }

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

            Set<Position> toBeRemoved = addedPositions.stream()
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

            Set<Position> toBeRemoved = addedPositions.stream()
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
