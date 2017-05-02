package geometry;

import model.Position;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelepipedTest {
    @Test
    public void testConstructor() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0));
        assertEquals(new Position(0,0,0), parallelepiped.getCenter());
        assertEquals(1, parallelepiped.getXSize());
        assertEquals(1, parallelepiped.getYSize());
        assertEquals(1, parallelepiped.getZSize());

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
    }

    @Test
    public void testConstructorHard() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0), 10, 20, 30);
        assertEquals(new Position(0,0,0), parallelepiped.getCenter());
        assertEquals(10, parallelepiped.getXSize());
        assertEquals(20, parallelepiped.getYSize());
        assertEquals(30, parallelepiped.getZSize());

        // TRUE cases

        assertTrue(parallelepiped.contains(new Position(0,   0,  0)));
        assertTrue(parallelepiped.contains(new Position(2,   1,  -3)));
        assertTrue(parallelepiped.contains(new Position(-2,   -4,  -2)));

        assertTrue(parallelepiped.contains(new Position(-5,-10,-15)));
        assertTrue(parallelepiped.contains(new Position(-5,-10, 15)));
        assertTrue(parallelepiped.contains(new Position(-5, 10, 15)));
        assertTrue(parallelepiped.contains(new Position(-5, 10,-15)));
        assertTrue(parallelepiped.contains(new Position(5,  10, 15)));
        assertTrue(parallelepiped.contains(new Position(5,  10,-15)));
        assertTrue(parallelepiped.contains(new Position(5, -10,-15)));
        assertTrue(parallelepiped.contains(new Position(5, -10, 15)));

        assertTrue(parallelepiped.contains(new Position(0,-10,15)));
        assertTrue(parallelepiped.contains(new Position(5,  0,15)));
        assertTrue(parallelepiped.contains(new Position(5,-10, 0)));

        assertTrue(parallelepiped.contains(new Position(0,0,15)));
        assertTrue(parallelepiped.contains(new Position(5,  0,0)));
        assertTrue(parallelepiped.contains(new Position(0,-10, 0)));

        // FALSE cases

        assertFalse(parallelepiped.contains(new Position(0,   100,  0)));
        assertFalse(parallelepiped.contains(new Position(200,   1,  -3)));
        assertFalse(parallelepiped.contains(new Position(-2,   -4,  -2000)));

        assertFalse(parallelepiped.contains(new Position(-6,-10,-15)));
        assertFalse(parallelepiped.contains(new Position(-5,-11, 15)));
        assertFalse(parallelepiped.contains(new Position(-5, 10, 16)));
        assertFalse(parallelepiped.contains(new Position(-6, 11,-16)));
        assertFalse(parallelepiped.contains(new Position(5,  11, 17)));
        assertFalse(parallelepiped.contains(new Position(6,  9,-15)));
        assertFalse(parallelepiped.contains(new Position(2, -11,-14)));
        assertFalse(parallelepiped.contains(new Position(6, -11, 16)));

        assertFalse(parallelepiped.contains(new Position(0,-11,16)));
        assertFalse(parallelepiped.contains(new Position(6,  0,15)));
        assertFalse(parallelepiped.contains(new Position(5,-12, 0)));

        assertFalse(parallelepiped.contains(new Position(0,0,16)));
        assertFalse(parallelepiped.contains(new Position(6,  0,0)));
        assertFalse(parallelepiped.contains(new Position(0,-11, 0)));
    }
}
