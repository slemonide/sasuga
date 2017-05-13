package geometry;

import com.jme3.math.Vector3f;
import model.Position;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static geometry.Axis.X;
import static geometry.Axis.Y;
import static geometry.Axis.Z;
import static org.junit.Assert.*;

public class ParallelepipedTest {
    @Test
    public void testConstructor() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0));
        assertEquals(new Position(0,0,0), parallelepiped.getCorner());
        assertEquals(1, parallelepiped.getSize(X));
        assertEquals(1, parallelepiped.getSize(Y));
        assertEquals(1, parallelepiped.getSize(Z));

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
    }

    @Test
    public void testConstructorHard() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(-4,-9,-14), 10, 20, 30);
        assertEquals(new Position(-4,-9,-14), parallelepiped.getCorner());
        assertEquals(10, parallelepiped.getSize(X));
        assertEquals(20, parallelepiped.getSize(Y));
        assertEquals(30, parallelepiped.getSize(Z));

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
        assertFalse(parallelepiped.contains(new Position(6,  0,-15)));
        assertFalse(parallelepiped.contains(new Position(5,-12, 0)));

        assertFalse(parallelepiped.contains(new Position(0,0,16)));
        assertFalse(parallelepiped.contains(new Position(6,  0,0)));
        assertFalse(parallelepiped.contains(new Position(0,-11, 0)));
    }

    @Test
    public void testTwoX() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0), 2, 1, 1);
        assertEquals(new Position(0,0,0), parallelepiped.getCorner());
        assertEquals(2, parallelepiped.getSize(X));
        assertEquals(1, parallelepiped.getSize(Y));
        assertEquals(1, parallelepiped.getSize(Z));

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
        assertTrue(parallelepiped.contains(new Position(1,0,0)));
        assertFalse(parallelepiped.contains(new Position(2,0,0)));
        assertFalse(parallelepiped.contains(new Position(-1,0,0)));
    }

    @Test
    public void testTwoY() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0), 1, 2, 1);
        assertEquals(new Position(0,0,0), parallelepiped.getCorner());
        assertEquals(1, parallelepiped.getSize(X));
        assertEquals(2, parallelepiped.getSize(Y));
        assertEquals(1, parallelepiped.getSize(Z));

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
        assertEquals(new Position(0,0,0), parallelepiped.getCorner());
        assertEquals(1, parallelepiped.getSize(X));
        assertEquals(1, parallelepiped.getSize(Y));
        assertEquals(2, parallelepiped.getSize(Z));

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
        assertTrue(parallelepiped.contains(new Position(0,0,1)));
        assertFalse(parallelepiped.contains(new Position(0,0,2)));
        assertFalse(parallelepiped.contains(new Position(0,0,-1)));
    }

    @Test
    public void testNonZeroPosition() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(10,20,30), 1, 1, 2);
        assertEquals(new Position(10,20,30), parallelepiped.getCorner());
        assertEquals(1, parallelepiped.getSize(X));
        assertEquals(1, parallelepiped.getSize(Y));
        assertEquals(2, parallelepiped.getSize(Z));

        assertTrue(parallelepiped.contains(new Position(10,20,30)));
        assertTrue(parallelepiped.contains(new Position(10,20,31)));
        assertFalse(parallelepiped.contains(new Position(10,20,32)));
        assertFalse(parallelepiped.contains(new Position(10,20,29)));
    }

    @Test
    public void testGetWorldVector3f() {
        Parallelepiped parallelepiped = new Parallelepiped(new Position(0,0,0));

        assertEquals(new Vector3f(0f,0f,0f), parallelepiped.getWorldVector3f());

        parallelepiped = new Parallelepiped(new Position(10, 11, 12));
        assertEquals(new Vector3f(10f,11f,12f), parallelepiped.getWorldVector3f());

        parallelepiped = new Parallelepiped(new Position(-10, -11, -12));
        assertEquals(new Vector3f(-10,-11,-12), parallelepiped.getWorldVector3f());

        parallelepiped = new Parallelepiped(new Position(0, 0, 0), 2, 1, 1);
        assertEquals(new Vector3f(0.5f,0f,0f), parallelepiped.getWorldVector3f());

        parallelepiped = new Parallelepiped(new Position(0, 0, 0), 20, 10, 30);
        assertEquals(new Vector3f(10f - 0.5f,5f - 0.5f,15f - 0.5f), parallelepiped.getWorldVector3f());
    }

    @Test
    public void testGetVolume() {
        assertEquals(1,
                new Parallelepiped(new Position(0,0,0)).getVolume());
        assertEquals(2,
                new Parallelepiped(new Position(0,0,0), 1, 2, 1).getVolume());
        assertEquals(10 * 4 * 7,
                new Parallelepiped(new Position(0,0,0), 10, 4, 7).getVolume());
    }

    @Test
    public void testEquals() {
        assertEquals(new Parallelepiped(new Position(0,0,0)),
                new Parallelepiped(new Position(0,0,0)));
        assertNotEquals(new Parallelepiped(new Position(1,0,0)),
                new Parallelepiped(new Position(0,0,0)));

        assertEquals(new Parallelepiped(new Position(0,3,0)),
                new Parallelepiped(new Position(0,3,0)));
        assertNotEquals(new Parallelepiped(new Position(0,3,0)),
                new Parallelepiped(new Position(0,3,4)));

        assertEquals(new Parallelepiped(new Position(0,3,0), 3, 4, 1),
                new Parallelepiped(new Position(0,3,0), 3, 4, 1));
        assertNotEquals(new Parallelepiped(new Position(0,3,0), 3, 4, 1),
                new Parallelepiped(new Position(0,3,0), 4, 4, 1));
    }

    @Test
    public void testHashCode() {
        Set<Parallelepiped> set = new HashSet<>();

        set.add(new Parallelepiped(new Position(0,0,0)));
        set.add(new Parallelepiped(new Position(0,0,1)));

        assertTrue(set.contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testSetCorner() {
        Parallelepiped testParallelepiped = new Parallelepiped(new Position(0,0,0));

        testParallelepiped = testParallelepiped.setCorner(new Position(-1,-2,3));
        assertEquals(new Position(-1,-2,3), testParallelepiped.getCorner());
    }

    @Test
    public void testSetSize() {
        Parallelepiped testParallelepiped = new Parallelepiped(new Position(0,0,0));
        Random random = new Random();

        for (Axis axis : Axis.values()) {
            int size = Math.abs(random.nextInt());

            testParallelepiped = testParallelepiped.setSize(axis, size);
            assertEquals(size, testParallelepiped.getSize(axis));
        }
    }

    // Intersects

    @Test
    public void testIntersectsNoIntersect() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0,0,0), 5, 3, 10);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(9, 5, 1), 2, 2, 11);

        assertFalse(parallelepipedA.intersects(parallelepipedB));
        assertFalse(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsCornerIntersect() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0,0,0), 5, 4, 10);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(3, 3, 9), 4, 5, 4);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsSideIntersect() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0, 0, 0), 5, 4, 10);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(3, 3, 9), 2, 2, 30);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsFullyInside() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0,0,0), 10, 11, 12);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(2, 2, 2), 2, 2, 3);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsPartialSideIntersect() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0,0,0), 7, 4, 1);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(5, 1,0), 7, 2, 1);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsExactMatch() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0,0,0), 2, 2, 3);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(0,0,0), 2, 2, 3);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsTShapeA() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0,0,0), 1, 5, 9);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(0,0,0), 1, 9, 5);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsTShapeB() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0,0,0), 5, 1, 9);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(0,0,0), 9, 1, 5);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsTShapeC() {
        Parallelepiped parallelepipedA = new Parallelepiped(new Position(0,0,0), 9, 5, 1);
        Parallelepiped parallelepipedB = new Parallelepiped(new Position(0,0,0), 5, 9, 1);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }
}
