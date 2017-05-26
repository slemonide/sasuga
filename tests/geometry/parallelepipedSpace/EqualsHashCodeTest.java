package geometry.parallelepipedSpace;

import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Tests equals() and hashcode() methods of ParallelepipedSpace
 */
public class EqualsHashCodeTest {

    @Test
    public void testEquals() {
        ParallelepipedSpace parallelepipedSpaceA = new ParallelepipedSpace();
        parallelepipedSpaceA.add(new Position(0,0,0));
        parallelepipedSpaceA.add(new Parallelepiped(new Position(0,0,20), 10, 4, 5));
        parallelepipedSpaceA.add(new Parallelepiped(new Position(-31,0,20), 4, 4, 5));
        parallelepipedSpaceA.add(new Position(1,1,1));

        ParallelepipedSpace parallelepipedSpaceB = new ParallelepipedSpace();
        parallelepipedSpaceB.add(new Position(0,0,0));
        parallelepipedSpaceB.add(new Parallelepiped(new Position(0,0,20), 10, 4, 5));
        parallelepipedSpaceB.add(new Parallelepiped(new Position(-31,0,20), 4, 4, 5));
        parallelepipedSpaceB.add(new Position(1,1,1));

        assertEquals(parallelepipedSpaceA, parallelepipedSpaceB);
    }

    @Test
    public void testEqualsTShapeSameOrder() {
        ParallelepipedSpace parallelepipedSpaceA = new ParallelepipedSpace();
        parallelepipedSpaceA.add(new Position(-1,0,0));
        parallelepipedSpaceA.add(new Position(0,0,0));
        parallelepipedSpaceA.add(new Position(1,0,0));
        parallelepipedSpaceA.add(new Position(0,1,0));
        parallelepipedSpaceA.add(new Position(0,1,0));

        ParallelepipedSpace parallelepipedSpaceB = new ParallelepipedSpace();
        parallelepipedSpaceB.add(new Position(-1,0,0));
        parallelepipedSpaceB.add(new Position(0,0,0));
        parallelepipedSpaceB.add(new Position(1,0,0));
        parallelepipedSpaceB.add(new Position(0,1,0));
        parallelepipedSpaceB.add(new Position(0,1,0));

        assertEquals(parallelepipedSpaceA, parallelepipedSpaceB);
    }

    @Test
    public void testEqualsTShapeDifferentOrder() {
        ParallelepipedSpace parallelepipedSpaceA = new ParallelepipedSpace();
        parallelepipedSpaceA.add(new Position(-1,0,0));
        parallelepipedSpaceA.add(new Position(0,0,0));
        parallelepipedSpaceA.add(new Position(1,0,0));

        parallelepipedSpaceA.add(new Position(0,1,0));
        parallelepipedSpaceA.add(new Position(0,1,0));

        ParallelepipedSpace parallelepipedSpaceB = new ParallelepipedSpace();
        parallelepipedSpaceB.add(new Position(0,0,0));
        parallelepipedSpaceB.add(new Position(0,1,0));
        parallelepipedSpaceB.add(new Position(0,1,0));

        parallelepipedSpaceB.add(new Position(-1,0,0));
        parallelepipedSpaceB.add(new Position(1,0,0));

        assertEquals(parallelepipedSpaceA, parallelepipedSpaceB);
    }

    @Test
    public void testNotEquals() {
        ParallelepipedSpace parallelepipedSpaceA = new ParallelepipedSpace();
        parallelepipedSpaceA.add(new Position(0,0,0));
        parallelepipedSpaceA.add(new Parallelepiped(new Position(0,0,20), 10, 4, 5));
        parallelepipedSpaceA.add(new Parallelepiped(new Position(-31,0,20), 4, 4, 5));
        parallelepipedSpaceA.add(new Position(1,1,3)); // difference

        ParallelepipedSpace parallelepipedSpaceB = new ParallelepipedSpace();
        parallelepipedSpaceB.add(new Position(0,0,0));
        parallelepipedSpaceB.add(new Parallelepiped(new Position(0,0,20), 10, 4, 5));
        parallelepipedSpaceB.add(new Parallelepiped(new Position(-31,0,20), 4, 4, 5));
        parallelepipedSpaceB.add(new Position(1,1,1)); // difference

        assertNotEquals(parallelepipedSpaceA, parallelepipedSpaceB);
    }

    @Test
    public void testNotEqualsTShapeSameOrder() {
        ParallelepipedSpace parallelepipedSpaceA = new ParallelepipedSpace();
        parallelepipedSpaceA.add(new Position(-1,0,0));
        parallelepipedSpaceA.add(new Position(0,0,0));
        parallelepipedSpaceA.add(new Position(1,0,0));
        parallelepipedSpaceA.add(new Position(0,1,0));
        parallelepipedSpaceA.add(new Position(0,1,0));

        parallelepipedSpaceA.add(new Position(0,1,0));

        ParallelepipedSpace parallelepipedSpaceB = new ParallelepipedSpace();
        parallelepipedSpaceB.add(new Position(-1,0,0));
        parallelepipedSpaceB.add(new Position(0,0,0));
        parallelepipedSpaceB.add(new Position(1,0,0));
        parallelepipedSpaceB.add(new Position(0,1,0));
        parallelepipedSpaceB.add(new Position(0,1,0));

        assertEquals(parallelepipedSpaceA, parallelepipedSpaceB);
    }

    @Test
    public void testNotEqualsTShapeDifferentOrder() {
        ParallelepipedSpace parallelepipedSpaceA = new ParallelepipedSpace();
        parallelepipedSpaceA.add(new Position(-1,0,0));
        parallelepipedSpaceA.add(new Position(0,0,0));
        parallelepipedSpaceA.add(new Position(1,0,0));

        parallelepipedSpaceA.add(new Position(0,1,0));
        parallelepipedSpaceA.add(new Position(0,1,0));

        parallelepipedSpaceA.add(new Position(0,1,3)); // difference

        ParallelepipedSpace parallelepipedSpaceB = new ParallelepipedSpace();
        parallelepipedSpaceB.add(new Position(0,0,0));
        parallelepipedSpaceB.add(new Position(0,1,0));
        parallelepipedSpaceB.add(new Position(0,1,0));

        parallelepipedSpaceB.add(new Position(-1,0,0));
        parallelepipedSpaceB.add(new Position(1,0,0));

        assertNotEquals(parallelepipedSpaceA, parallelepipedSpaceB);
    }

    @Test
    public void testHashCode() {
        ParallelepipedSpace parallelepipedSpaceA = new ParallelepipedSpace();
        parallelepipedSpaceA.add(new Position(-1,0,0));
        parallelepipedSpaceA.add(new Position(0,0,0));
        parallelepipedSpaceA.add(new Position(1,0,0));

        parallelepipedSpaceA.add(new Position(0,1,0));
        parallelepipedSpaceA.add(new Position(0,1,0));

        ParallelepipedSpace parallelepipedSpaceB = new ParallelepipedSpace();
        parallelepipedSpaceB.add(new Position(0,0,0));
        parallelepipedSpaceB.add(new Position(0,1,0));
        parallelepipedSpaceB.add(new Position(0,1,0));

        parallelepipedSpaceB.add(new Position(-1,0,0));
        parallelepipedSpaceB.add(new Position(1,0,0));

        Set<ParallelepipedSpace> parallelepipedSpaceSet = new HashSet<>();

        parallelepipedSpaceSet.add(parallelepipedSpaceA);

        assertTrue(parallelepipedSpaceSet.contains(parallelepipedSpaceA));
        assertTrue(parallelepipedSpaceSet.contains(parallelepipedSpaceB));
    }
}
