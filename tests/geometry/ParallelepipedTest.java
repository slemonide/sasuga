package geometry;

import com.jme3.math.Vector3f;
import geometry.parallelepipedSpace.Helpers;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static geometry.Axis.X;
import static geometry.Axis.Y;
import static geometry.Axis.Z;
import static geometry.parallelepipedSpace.Helpers.RANDOM;
import static org.junit.Assert.*;

public class ParallelepipedTest {
    private static final Random random = new Random();
    private Parallelepiped parallelepiped;
    private Parallelepiped parallelepipedA;
    private Parallelepiped parallelepipedB;

    @Test
    public void testConstructor() {
        parallelepiped = new Parallelepiped(new Position(0,0,0));
        assertEquals(new Position(0,0,0), parallelepiped.getCorner());
        assertEquals(1, parallelepiped.getSize(X));
        assertEquals(1, parallelepiped.getSize(Y));
        assertEquals(1, parallelepiped.getSize(Z));

        assertTrue(parallelepiped.contains(new Position(0,0,0)));
    }

    @Test
    public void testConstructorHard() {
        parallelepiped = new Parallelepiped(new Position(-4,-9,-14), 10, 20, 30);
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

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorExceptionX() throws IllegalArgumentException {
        new Parallelepiped(new Position(0,0,0), -10,2,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorExceptionY() throws IllegalArgumentException {
        new Parallelepiped(new Position(0,0,0), 10,-20,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorExceptionZ() throws IllegalArgumentException {
        new Parallelepiped(new Position(0,0,0), 10,1,0);
    }

    @Test
    public void testTwoX() {
        parallelepiped = new Parallelepiped(new Position(0,0,0), 2, 1, 1);
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
        parallelepiped = new Parallelepiped(new Position(0,0,0), 1, 2, 1);
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
        parallelepiped = new Parallelepiped(new Position(0,0,0), 1, 1, 2);
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
        parallelepiped = new Parallelepiped(new Position(10,20,30), 1, 1, 2);
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
        parallelepiped = new Parallelepiped(new Position(0,0,0));

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
                new Parallelepiped(new Position(0,0,0)).volume());
        assertEquals(2,
                new Parallelepiped(new Position(0,0,0), 1, 2, 1).volume());
        assertEquals(10 * 4 * 7,
                new Parallelepiped(new Position(0,0,0), 10, 4, 7).volume());
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

    // intersects

    @Test
    public void testIntersectsNoIntersect() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 5, 3, 10);
        parallelepipedB = new Parallelepiped(new Position(9, 5, 1), 2, 2, 11);

        assertFalse(parallelepipedA.intersects(parallelepipedB));
        assertFalse(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsCornerIntersect() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 5, 4, 10);
        parallelepipedB = new Parallelepiped(new Position(3, 3, 9), 4, 5, 4);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsSideIntersect() {
        parallelepipedA = new Parallelepiped(new Position(0, 0, 0), 5, 4, 10);
        parallelepipedB = new Parallelepiped(new Position(3, 3, 9), 2, 2, 30);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsFullyInside() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 10, 11, 12);
        parallelepipedB = new Parallelepiped(new Position(2, 2, 2), 2, 2, 3);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsPartialSideIntersect() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 7, 4, 1);
        parallelepipedB = new Parallelepiped(new Position(5, 1,0), 7, 2, 1);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsExactMatch() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 2, 2, 3);
        parallelepipedB = new Parallelepiped(new Position(0,0,0), 2, 2, 3);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsTShapeA() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 1, 5, 9);
        parallelepipedB = new Parallelepiped(new Position(0,0,0), 1, 9, 5);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsTShapeB() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 5, 1, 9);
        parallelepipedB = new Parallelepiped(new Position(0,0,0), 9, 1, 5);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    @Test
    public void testIntersectsTShapeC() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 9, 5, 1);
        parallelepipedB = new Parallelepiped(new Position(0,0,0), 5, 9, 1);

        assertTrue(parallelepipedA.intersects(parallelepipedB));
        assertTrue(parallelepipedB.intersects(parallelepipedA));
    }

    // fits

    @Test
    public void testFitsBasic() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0));
        parallelepipedB = new Parallelepiped(new Position(0,0,0));

        for (Axis axis : Axis.values()) {
            assertTrue(parallelepipedA.fits(axis, parallelepipedB));
            assertTrue(parallelepipedB.fits(axis, parallelepipedA));
        }
    }

    @Test
    public void testFitsHard() {
        for (Axis axis : Axis.values()) {
            parallelepipedA = new Parallelepiped(new Position(
                    random.nextInt(Integer.MAX_VALUE),
                    random.nextInt(Integer.MAX_VALUE),
                    random.nextInt(Integer.MAX_VALUE)),
                    random.nextInt(Integer.MAX_VALUE),
                    random.nextInt(Integer.MAX_VALUE),
                    random.nextInt(Integer.MAX_VALUE));
            parallelepipedB = new Parallelepiped(new Position(
                    random.nextInt(Integer.MAX_VALUE),
                    random.nextInt(Integer.MAX_VALUE),
                    random.nextInt(Integer.MAX_VALUE)),
                    random.nextInt(Integer.MAX_VALUE),
                    random.nextInt(Integer.MAX_VALUE),
                    random.nextInt(Integer.MAX_VALUE));

            for (Axis complement : axis.getComplements()) {
                int size = random.nextInt(Integer.MAX_VALUE);
                int coordinate = random.nextInt(Integer.MAX_VALUE);
                parallelepipedA = parallelepipedA.setSize(complement, size);
                parallelepipedB = parallelepipedB.setSize(complement, size);
                parallelepipedA = parallelepipedA.setCorner(parallelepipedA.getCorner().set(complement, coordinate));
                parallelepipedB = parallelepipedB.setCorner(parallelepipedB.getCorner().set(complement, coordinate));
            }

            assertTrue(parallelepipedA.fits(axis, parallelepipedB));
            assertTrue(parallelepipedB.fits(axis, parallelepipedA));

            for (Axis complement : axis.getComplements()) {
                assertFalse(parallelepipedA.fits(complement, parallelepipedB));
                assertFalse(parallelepipedB.fits(complement, parallelepipedA));
            }
        }
    }

    @Test
    public void testFitsNoFitBasic() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0));
        parallelepipedB = new Parallelepiped(new Position(0,0,0), 2, 1, 2);

        for (Axis axis : Axis.values()) {
            assertFalse(parallelepipedA.fits(axis, parallelepipedB));
            assertFalse(parallelepipedB.fits(axis, parallelepipedA));
        }
    }

    @Test
    public void testFitsCubeFitFit() {
        parallelepipedA = new Parallelepiped(new Position(0,0,0), 3, 3, 3);
        parallelepipedB = new Parallelepiped(new Position(0,0,0), 3, 3, 3);

        for (Axis axis : Axis.values()) {
            assertTrue(parallelepipedA.fits(axis, parallelepipedB));
            assertTrue(parallelepipedB.fits(axis, parallelepipedA));
        }
    }

    // getInterlockingNeighbours

    @Test
    public void testGetInterlockingNeighboursHard() {
        ParallelepipedSpace testSpace = new ParallelepipedSpace();

        parallelepiped = new Parallelepiped(new Position(0,0,0));
        testSpace.add(new Position(0,0,0));
        for (Axis axis : Axis.values()) {
            assertTrue(parallelepiped.getInterlockingNeighbours(testSpace,axis).isEmpty());
        }

        for (Axis iteratedAxis : Axis.values()) {
            parallelepiped = new Parallelepiped(new Position(0, 0, 0).set(iteratedAxis, 1));
            for (Axis axis : Axis.values()) {
                if (axis != iteratedAxis) {
                    assertTrue(parallelepiped.getInterlockingNeighbours(testSpace, axis).isEmpty());
                } else {
                    assertEquals(1, parallelepiped.getInterlockingNeighbours(testSpace, axis).size());
                    assertTrue(parallelepiped.getInterlockingNeighbours(testSpace, axis)
                            .contains(new Parallelepiped(new Position(0, 0, 0))));
                }
            }
        }
    }

    @Test
    public void testGetInterlockingNeighboursWeird() {
        ParallelepipedSpace testSpace = new ParallelepipedSpace();

        parallelepiped = new Parallelepiped(new Position(0,0,0));
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));

        for (Axis axis : Axis.values()) {
            if (axis != Axis.Y) {
                assertTrue(parallelepiped.getInterlockingNeighbours(testSpace, axis).isEmpty());
            } else {
                assertEquals(1, parallelepiped.getInterlockingNeighbours(testSpace, axis).size());
                assertTrue(parallelepiped.getInterlockingNeighbours(testSpace, axis)
                        .contains(new Parallelepiped(new Position(0, 0, 0), 1, 2, 1)));
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositionFromIndexTooBigException() {
        parallelepiped = new Parallelepiped(new Position(0,0,0));

        //noinspection ResultOfMethodCallIgnored
        parallelepiped.positionFromIndex(2);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPositionFromIndexTooSmallException() {
        parallelepiped = new Parallelepiped(new Position(0,0,0));

        //noinspection ResultOfMethodCallIgnored
        parallelepiped.positionFromIndex(0);
        fail();
    }

    @Test
    public void testPositionFromIndex() {
        parallelepiped = new Parallelepiped(new Position(0,0,0), 11, 21, 27);

        Set<Position> positions = new HashSet<>();

        for (int i = 1; i <= parallelepiped.volume(); i++) {
            positions.add(parallelepiped.positionFromIndex(i));
        }

        assertEquals(positions.size(), parallelepiped.volume());
    }

    @Test
    public void testPositionFromIndexNonZeroPosition() {
        parallelepiped = new Parallelepiped(new Position(-30,4,5), 11, 21, 27);

        Set<Position> positions = new HashSet<>();

        for (int i = 1; i <= parallelepiped.volume(); i++) {
            positions.add(parallelepiped.positionFromIndex(i));
        }

        assertEquals(positions.size(), parallelepiped.volume());
        assertTrue(positions.contains(new Position(-30,4,5)));
    }

    @Test
    public void testPositions() {
        parallelepiped = new Parallelepiped(new Position(0,0,0), 7, 9, 11);

        int volume = parallelepiped.positions().mapToInt(position -> 1).sum();

        assertEquals(volume, parallelepiped.volume());
    }
    
    @Test
    public void testAverageCenterPositionOneParallelepiped() {
        parallelepiped = new Parallelepiped(new Position(2,3,4),1,2,5);

        Position center = parallelepiped.center();
        Position averageCenter = parallelepiped.averageCenterPosition(parallelepiped);
        assertEquals(center, averageCenter);
    }

    @Test
    public void testAverageCenterPositionTwoParallelepipedsNotWeighted() {
        Position positionA = new Position(2, 3, 2);
        Position positionB = new Position(-100, 30, 7);

        parallelepipedA = new Parallelepiped(positionA);
        parallelepipedB = new Parallelepiped(positionB);

        Position center = positionA.add(positionB).divide(2);

        assertEquals(center, parallelepipedA.averageCenterPosition(parallelepipedB));
    }

    @Test
    public void testAverageCenterPositionApplicationInvariance() {
        Position positionA = new Position(2, 3, 2);
        Position positionB = new Position(-100, 30, 7);

        parallelepipedA = new Parallelepiped(positionA, 143, 3, 32);
        parallelepipedB = new Parallelepiped(positionB, 53, 7, 10);

        assertEquals(
                parallelepipedA.averageCenterPosition(parallelepipedB),
                parallelepipedB.averageCenterPosition(parallelepipedA));
    }

    @Test
    public void testAverageCenterPositionTwoParallelepipedsWeighted() {
        Position positionA = new Position(2, 3, 2);
        Position positionB = new Position(-100, 30, 7);

        parallelepipedA = new Parallelepiped(positionA, 123, 2, 32);
        parallelepipedB = new Parallelepiped(positionB, 5, 7, 9);

        Position center = ((parallelepipedA.center().multiply(parallelepipedA.volume()))
                .add(parallelepipedB.center().multiply(parallelepipedB.volume())))
                .divide(parallelepipedA.volume() + parallelepipedB.volume());

        Position average = parallelepipedA.averageCenterPosition(parallelepipedB);

        assertEquals(center, average);
    }

    @Test
    public void testContainsPositionItself() {
        parallelepipedA = Helpers.getRandomParallelepiped();

        assertTrue(parallelepipedA.contains(parallelepipedA.getCorner()));
        assertTrue(parallelepipedA.contains(parallelepipedA.center()));
        assertTrue(parallelepipedA.contains(parallelepipedA.getCorner()
                .add(parallelepipedA.getSides())));

        for (Axis axisA : Axis.values()) {
            for (Axis axisB : Axis.values()) {
                assertTrue(parallelepipedA.contains(parallelepipedA.getCorner()
                        .add(parallelepipedA.getSides()
                                .set(axisA, 0)
                                .set(axisB, 0))));
            }
        }
    }

    @Test
    public void testContainsPositionItselfHarder() {
        parallelepipedA = Helpers.getRandomParallelepiped();

        assertTrue(parallelepipedA.positions()
                .filter(position ->
                        RANDOM.nextInt(
                                Math.abs(Math.max(parallelepipedA.getSize(X),
                                         parallelepipedA.getSize(Y))))
                                == 1)
                .allMatch(parallelepipedA::contains));
    }

    @Test
    public void testContainsParallelepipedPositiveFullCover() {
        parallelepipedA = Helpers.getRandomParallelepiped();
        parallelepipedB = parallelepipedA;

        assertTrue(parallelepipedA.contains(parallelepipedB));
        assertTrue(parallelepipedB.contains(parallelepipedA));
    }

    @Test
    public void testContainsParallelepipedPositiveHalfCoverSideTouch() {
        parallelepipedA = Helpers.getRandomParallelepiped();
        parallelepipedB = parallelepipedA.setSides(parallelepipedA.getSides().divide(2));

        assertTrue(parallelepipedA.contains(parallelepipedB));
        assertFalse(parallelepipedB.contains(parallelepipedA));
    }

    @Test
    public void testContainsParallelepipedPositiveHalfCoverInside() {
        parallelepipedA = Helpers.getRandomParallelepiped();
        parallelepipedB = parallelepipedA
                .setSides(parallelepipedA.getSides().divide(2))
                .setCorner(parallelepipedA.getCorner().add(1, 2, 3));

        assertTrue(parallelepipedA.contains(parallelepipedB));
        assertFalse(parallelepipedB.contains(parallelepipedA));
    }

    @Test
    public void testContainsParallelepipedPositiveHalfIntersect() {
        parallelepipedA = Helpers.getRandomParallelepiped();
        parallelepipedB = Helpers.getRandomParallelepiped()
                .setCorner(parallelepipedA.getCorner()
                        .add(1, 2, 3)
                        .add(Helpers.getRandomPosition(parallelepipedA.getSides())));

        assertFalse(parallelepipedA.contains(parallelepipedB));
        assertFalse(parallelepipedB.contains(parallelepipedA));
    }

    @Test
    public void testContainsParallelepipedPositiveNoIntersect() {
        parallelepipedA = Helpers.getRandomParallelepiped();
        parallelepipedB = Helpers.getRandomParallelepiped()
                .setCorner(parallelepipedA.getCorner()
                        .add(1, 2, 3)
                        .add(Helpers.getRandomPosition()));

        assertFalse(parallelepipedA.contains(parallelepipedB));
        assertFalse(parallelepipedB.contains(parallelepipedA));
    }
}
