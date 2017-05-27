package geometry;

import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Tests for the Position class
 */
public class PositionTest {
    private static final int MAX_RANDOM_POSITIVE_CASES = 100;
    private static final int MAX_RANDOM_POSITIVE_BOUND = Integer.MAX_VALUE;

    @Test
    public void testConstructor() {
        Position position = new Position(12, 3, 2);
        assertEquals(12, position.x);
        assertEquals(3, position.y);
        assertEquals(2, position.z);
    }

    @Test
    public void testAdd() {
        Position position = new Position(1, 2, 0);
        assertEquals(new Position(1, 2, 0), position.add(0,0,0));
        assertEquals(new Position(2, 2, 0), position.add(1,0,0));
        assertEquals(new Position(0, 3, 0), position.add(-1, 1,0));
        assertEquals(new Position(4, 4, 1), position.add(3, 2, 1));
    }

    @Test
    public void testAddVector() {
        Position position = new Position(1, 2,0);
        assertEquals(new Position(1, 2, 0), position.add(new Position(0,0,0)));
        assertEquals(new Position(2, 2, 0), position.add(new Position(1,0,0)));
        assertEquals(new Position(0, 3, 0), position.add(new Position(-1, 1,0)));
        assertEquals(new Position(4, 4, 1), position.add(new Position(3, 2, 1)));
    }

    @Test
    public void testHashCode() {
        Set<Position> positionSet = new HashSet<>();

        positionSet.add(new Position(1, 1,0));
        positionSet.add(new Position(1, 2,9));

        assertTrue(positionSet.contains(new Position(1, 1, 0)));
    }

    @Test
    public void testDivideToZeroByTwo() {
        Position position0 = new Position(100, 200, 300);
        Position position1 = new Position(50, 100, 150);
        Position position2 = new Position(25, 50, 75);
        Position position3 = new Position(12, 25, 37);
        Position position4 = new Position(6, 12, 18);
        Position position5 = new Position(3, 6, 9);
        Position position6 = new Position(1, 3, 4);
        Position position7 = new Position(0, 1, 2);
        Position position8 = new Position(0, 0, 1);
        Position position9 = new Position(0, 0, 0);
        Position position10 = new Position(0, 0, 0);

        assertEquals(position1,  position0.divide(2));
        assertEquals(position2,  position1.divide(2));
        assertEquals(position3,  position2.divide(2));
        assertEquals(position4,  position3.divide(2));
        assertEquals(position5,  position4.divide(2));
        assertEquals(position6,  position5.divide(2));
        assertEquals(position7,  position6.divide(2));
        assertEquals(position8,  position7.divide(2));
        assertEquals(position9,  position8.divide(2));
        assertEquals(position10, position9.divide(2));
    }

    @Test
    public void testDivideByDifferentFactors() {
        Position position0 = new Position(24, 70, 50);
        Position position1 = new Position(4, 14, 10); // by 5
        Position position2 = new Position(1, 4, 3); // by 3
        Position position3 = new Position(1, 4, 3); // by 1
        Position position4 = new Position(0, 1, 0); // by 4
        Position position5 = new Position(0, 0, 0); // by 2

        Position next = position0.divide(5);
        assertEquals(position1, next);
        assertEquals(position2, position1.divide(3));
        assertEquals(position3, position2.divide(1));
        assertEquals(position4, position3.divide(4));
        assertEquals(position5, position4.divide(2));
    }

    @Test
    public void testMinBasic() {
        Position positionA = new Position(0,0,0);
        Position positionB = new Position(1, 32, 5);

        assertEquals(positionA, positionA.min(positionB));
        assertEquals(positionA, positionB.min(positionA));

        assertTrue(positionA.lessThanOrEqual(positionB));
        assertFalse(positionB.lessThanOrEqual(positionA));
    }

    @Test
    public void testMinEachCoordinate() {
        Position positionA = new Position(0,0,0);

        for (Axis axis : Axis.values()) {
            Position positionB = positionA.set(axis, 1);

            assertEquals(positionA, positionA.min(positionB));
            assertEquals(positionA, positionB.min(positionA));

            assertTrue(positionA.lessThanOrEqual(positionB));
            assertFalse(positionB.lessThanOrEqual(positionA));
        }
    }

    @Test
    public void testMinRandomPositiveCoordinate() {
        Position positionA = new Position(0,0,0);

        Random random = new Random();

        for (int i = 0; i < MAX_RANDOM_POSITIVE_CASES; i++) {
            Position positionB = new Position(
                    random.nextInt(MAX_RANDOM_POSITIVE_BOUND),
                    random.nextInt(MAX_RANDOM_POSITIVE_BOUND),
                    random.nextInt(MAX_RANDOM_POSITIVE_BOUND)
            );

            assertEquals(positionA, positionA.min(positionB));
            assertEquals(positionA, positionB.min(positionA));

            assertTrue(positionA.lessThanOrEqual(positionB));
            assertFalse(positionB.lessThanOrEqual(positionA));
        }
    }
    
    @Test
    public void testTransitivity() {
        Random random = new Random();

        for (int i = 0; i < MAX_RANDOM_POSITIVE_CASES; i++) {
            Position positionA = new Position(
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt()
            );

            Position positionB = new Position(
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt()
            );

            Position positionC = new Position(
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt()
            );

            if (positionA.lessThanOrEqual(positionB) &&
                    positionB.lessThanOrEqual(positionC)) {
                assertTrue(positionA.lessThanOrEqual(positionB));
            }
        }
    }
}
