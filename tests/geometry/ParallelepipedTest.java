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

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
    }
}
