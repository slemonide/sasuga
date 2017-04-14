package server.tests;

import org.junit.Test;
import server.model.Vector;
import static org.junit.Assert.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Tests for the Vector class
 */
public class VectorTest {
    @Test
    public void testConstructor() {
        Vector vector = new Vector(12, 3, 2);
        assertEquals(12, vector.v[0]);
        assertEquals(3, vector.v[1]);
        assertEquals(2, vector.v[2]);
        assertEquals(3, vector.v.length);
    }

    @Test
    public void testAdd() {
        Vector vector = new Vector(1, 2);
        assertEquals(new Vector(1, 2), vector.add());
        assertEquals(new Vector(2, 2), vector.add(1));
        assertEquals(new Vector(0, 3), vector.add(-1, 1));
        assertEquals(new Vector(4, 4, 1), vector.add(3, 2, 1));
    }

    @Test
    public void testAddVector() {
        Vector vector = new Vector(1, 2);
        assertEquals(new Vector(1, 2), vector.add(new Vector()));
        assertEquals(new Vector(2, 2), vector.add(new Vector(1)));
        assertEquals(new Vector(0, 3), vector.add(new Vector(-1, 1)));
        assertEquals(new Vector(4, 4, 1), vector.add(new Vector(3, 2, 1)));
    }
}
