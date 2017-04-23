package tests;

import org.junit.Test;
import model.Position;

import java.util.HashSet;
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
    @Test
    public void testConstructor() {
        Position position = new Position(12, 3, 2);
        assertEquals(12, position.getComponent(0));
        assertEquals(3, position.getComponent(1));
        assertEquals(2, position.getComponent(2));
        assertEquals(3, position.getNonZeroComponents().size());
        assertEquals(0, position.getComponent(100));
        assertEquals(0, position.getComponent(-200));
    }

    @Test
    public void testAdd() {
        Position position = new Position(1, 2);
        assertEquals(new Position(1, 2), position.add());
        assertEquals(new Position(2, 2), position.add(1));
        assertEquals(new Position(0, 3), position.add(-1, 1));
        assertEquals(new Position(4, 4, 1), position.add(3, 2, 1));
    }

    @Test
    public void testAddVector() {
        Position position = new Position(1, 2);
        assertEquals(new Position(1, 2), position.add(new Position()));
        assertEquals(new Position(2, 2), position.add(new Position(1)));
        assertEquals(new Position(0, 3), position.add(new Position(-1, 1)));
        assertEquals(new Position(4, 4, 1), position.add(new Position(3, 2, 1)));
    }

    @Test
    public void testHashCode() {
        Set<Position> positionSet = new HashSet<>();

        positionSet.add(new Position(1, 1));
        positionSet.add(new Position(1, 2, 4, 9));

        assertTrue(positionSet.contains(new Position(1, 1)));
    }
}
