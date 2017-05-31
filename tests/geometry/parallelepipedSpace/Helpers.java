package geometry.parallelepipedSpace;

import geometry.Axis;
import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Helpers for the ParallelepipedSpaceTest class
 */
public class Helpers {
    public final static Random RANDOM = new Random();

    // -----------------------------------------------------------------------
    // User-defined constants

    /**
     * Default number of iterations for random-pick tests
     */
    public static final int MAX_RANDOM_ITERATIONS = 10000;

    /**
     * Default number of iterations for computationally-demanding
     * random-pick tests
     */
    public static final int MAX_RANDOM_ITERATIONS_HARD = 100;

    /**
     * Specifies how many items to generate at maximum
     */
    public static final int MAX_ENTITIES = 100;


    /**
     * Specifies tha maximum edge used for the position-removing equivalence test
     */
    public static final int RANDOM_REMOVE_MAX_EDGE = 5;

    /**
     * Default bound with which to search for a random number
     */
    public static final int BOUND = 1000;

    static final int MAX_POSITIONS_X = 10;
    static final int MAX_POSITIONS_Y = 20;
    static final int MAX_POSITIONS_Z = 17;

    /**
     * Randomly-generated corner
     */
    private Position corner;


    static void buildCube(ParallelepipedSpace space, int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    space.add(new Position(x, y, z));
                }
            }
        }
    }

    static Position nextPosition() {
        switch (RANDOM.nextInt(6)) {
            case 0:
                return new Position(1, 0, 0);
            case 1:
                return new Position(-1, 0, 0);
            case 2:
                return new Position(0, 1, 0);
            case 3:
                return new Position(0, -1, 0);
            case 4:
                return new Position(0, 0, 1);
            default:
                return new Position(0, 0, -1);
        }
    }

    static void cutParallelepipedRight(ParallelepipedSpace space) {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int z = 0; z < MAX_POSITIONS_Z; z++) {
                space.remove(new Position(x, 0, z));
            }
        }
    }

    static void cutParallelepipedLeft(ParallelepipedSpace space) {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int z = 0; z < MAX_POSITIONS_Z; z++) {
                space.remove(new Position(x, MAX_POSITIONS_Y - 1, z));
            }
        }
    }

    static void cutParallelepipedTop(ParallelepipedSpace space) {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int y = 0; y < MAX_POSITIONS_Y; y++) {
                space.remove(new Position(x, y, MAX_POSITIONS_Z - 1));
            }
        }
    }

    static void cutParallelepipedBottom(ParallelepipedSpace space) {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int y = 0; y < MAX_POSITIONS_Y; y++) {
                space.remove(new Position(x, y, 0));
            }
        }
    }

    static void buildRandomCloud(ParallelepipedSpace space) {
        Position center = new Position(0,0,0);
        Parallelepiped parallelepiped = new Parallelepiped(center, 13, 7, 9);

        Stream.iterate(1, i -> i+1).limit(parallelepiped.volume()).forEach(index -> {
            if (RANDOM.nextInt(10) == 1) {
                space.add(parallelepiped.positionFromIndex(index));
            }
        });
    }

    static void buildParallelepiped(ParallelepipedSpace space) {
        for (int x = 0; x < Helpers.MAX_POSITIONS_X; x++) {
            for (int y = 0; y < Helpers.MAX_POSITIONS_Y; y++) {
                for (int z = 0; z < Helpers.MAX_POSITIONS_Z; z++) {
                    space.add(new Position(x, y, z));
                }
            }
        }
    }

    static void buildUnitCube(ParallelepipedSpace space) {
        space.add(new Position(1,0,0));
        space.add(new Position(0,0,0));
        space.add(new Position(1,1,0));
        space.add(new Position(0,1,0));
        space.add(new Position(1,0,1));
        space.add(new Position(0,0,1));
        space.add(new Position(1,1,1));
        space.add(new Position(0,1,1));
    }

    /**
     * Produces a random number in the interval (-bound, bound)
     * @param bound specifies the interval from which to chose the number
     * @return a random number in the interval (-bound, bound)
     */
    public static int getRandomNumber(int bound) {
        return (RANDOM.nextInt(1) * 2 - 1) * RANDOM.nextInt(bound);
    }

    /**
     * Produces a random position without any upper or lower bound
     * @return a random position
     */
    @NotNull
    public static Position getRandomPosition() {
        return new Position(RANDOM.nextInt(), RANDOM.nextInt(), RANDOM.nextInt());
    }

    /**
     * Produces a random position with components in the interval (-bound, bound)
     * @param bound specifies the interval from which to chose the components
     * @return a random position
     */
    @NotNull
    public static Position getRandomPosition(int bound) {
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
    public static Position getRandomPosition(@NotNull Position bound) {
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
    public static Parallelepiped getRandomParallelepiped(int maxSideLength) {
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
    public static Parallelepiped getRandomParallelepiped() {
        return getRandomParallelepiped(Integer.MAX_VALUE);
    }

    /**
     * Produces a random parallelepiped with the specified corner
     * @param corner of the parallelepiped
     * @return a random parallelepiped with the given corner
     */
    @NotNull
    public static Parallelepiped getRandomParallelepiped(@NotNull Position corner) {
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
    public static Parallelepiped getRandomParallelepiped(@NotNull Position center, int maxSideLength) {
        return new Parallelepiped(center.add(getRandomPosition(maxSideLength)),
                RANDOM.nextInt(maxSideLength) + 1,
                RANDOM.nextInt(maxSideLength) + 1,
                RANDOM.nextInt(maxSideLength) + 1);
    }

    // -----------------------------------------------------------------------
    // Test helper functions

    @Before
    public void runBefore() {
        corner = getRandomPosition();
    }

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
}
