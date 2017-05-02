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

        assertTrue(parallelepiped.contains(new Position(-4,-9,-14)));
        assertTrue(parallelepiped.contains(new Position(-4,-9, 15)));
        assertTrue(parallelepiped.contains(new Position(-4, 10, 15)));
        assertTrue(parallelepiped.contains(new Position(-4, 10,-14)));
        assertTrue(parallelepiped.contains(new Position(5,  10, 15)));
        assertTrue(parallelepiped.contains(new Position(5,  10,-14)));
        assertTrue(parallelepiped.contains(new Position(5, -9,-14)));
        assertTrue(parallelepiped.contains(new Position(5, -9, 15)));

        assertTrue(parallelepiped.contains(new Position(0,-9,15)));
        assertTrue(parallelepiped.contains(new Position(5, 0,15)));
        assertTrue(parallelepiped.contains(new Position(5,-9, 0)));

        assertTrue(parallelepiped.contains(new Position(0,0,15)));
        assertTrue(parallelepiped.contains(new Position(5,  0,0)));
        assertTrue(parallelepiped.contains(new Position(0,-9, 0)));

        // FALSE cases

        assertFalse(parallelepiped.contains(new Position(-5,-10,-15)));
        assertFalse(parallelepiped.contains(new Position(-5,-10, 15)));
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

    @Test
    public void testTwoX() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0), 2, 1, 1);
        assertEquals(new Position(0,0,0), parallelepiped.getCenter());
        assertEquals(2, parallelepiped.getXSize());
        assertEquals(1, parallelepiped.getYSize());
        assertEquals(1, parallelepiped.getZSize());

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
        assertTrue(parallelepiped.contains(new Position(1,0,0)));
        assertFalse(parallelepiped.contains(new Position(2,0,0)));
        assertFalse(parallelepiped.contains(new Position(-1,0,0)));
    }

    @Test
    public void testTwoY() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0), 1, 2, 1);
        assertEquals(new Position(0,0,0), parallelepiped.getCenter());
        assertEquals(1, parallelepiped.getXSize());
        assertEquals(2, parallelepiped.getYSize());
        assertEquals(1, parallelepiped.getZSize());

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
        assertTrue(parallelepiped.contains(new Position(0,1,0)));
        assertFalse(parallelepiped.contains(new Position(0,2,0)));
        assertFalse(parallelepiped.contains(new Position(0,-1,0)));

        assertFalse(parallelepiped.contains(new Position(1,0,0)));
        assertFalse(parallelepiped.contains(new Position(-1,1,0)));
        assertFalse(parallelepiped.contains(new Position(0,0,1)));
        assertFalse(parallelepiped.contains(new Position(0,1,-1)));
    }

    @Test
    public void testTwoZ() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0), 1, 1, 2);
        assertEquals(new Position(0,0,0), parallelepiped.getCenter());
        assertEquals(1, parallelepiped.getXSize());
        assertEquals(1, parallelepiped.getYSize());
        assertEquals(2, parallelepiped.getZSize());

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
        assertTrue(parallelepiped.contains(new Position(0,0,1)));
        assertFalse(parallelepiped.contains(new Position(0,0,2)));
        assertFalse(parallelepiped.contains(new Position(0,0,-1)));
    }

    @Test
    public void testNonZeroPosition() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(10,20,30), 1, 1, 2);
        assertEquals(new Position(10,20,30), parallelepiped.getCenter());
        assertEquals(1, parallelepiped.getXSize());
        assertEquals(1, parallelepiped.getYSize());
        assertEquals(2, parallelepiped.getZSize());

        assertTrue(parallelepiped.contains(new Position(10,20,30)));
        assertTrue(parallelepiped.contains(new Position(10,20,31)));
        assertFalse(parallelepiped.contains(new Position(10,20,32)));
        assertFalse(parallelepiped.contains(new Position(10,20,29)));
    }
}
