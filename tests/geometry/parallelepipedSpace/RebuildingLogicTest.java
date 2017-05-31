package geometry.parallelepipedSpace;

import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static geometry.parallelepipedSpace.ParallelepipedSpaceTest.MAX_POSITIONS;
import static geometry.parallelepipedSpace.Helpers.buildParallelepiped;
import static geometry.parallelepipedSpace.Helpers.buildUnitCube;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Parallelepiped rebuilding logic tests for ParallelepipedSpace
 */
public class RebuildingLogicTest {
    private static final int MAX_POSITIONS_CUBE = 17;
    private ParallelepipedSpace testSpace;

    @Before
    public void runBefore() {
        testSpace = new ParallelepipedSpace();
    }

    // ADD

    @Test
    public void testSimpleAdd() {
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testAddTwice() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testAddCubeTwice() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    testSpace.add(new Position(x, y, z));
                }
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                testSpace.add(new Position(x, 1, z));
            }
        }

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                3, 3, 3)));
    }

    @Test
    public void testAddTwoX() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,1,1)));
    }

    @Test
    public void testAddTwoY() {
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,2,1)));
    }

    @Test
    public void testAddTwoZ() {
        testSpace.add(new Position(0,0,1));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,1,2)));
    }

    @Test
    public void testAddTwoXReverse() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,1,1)));
    }

    @Test
    public void testAddTwoYReverse() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,2,1)));
    }

    @Test
    public void testAddTwoZReverse() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,0,1));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,1,2)));
    }

    @Test
    public void testAddThree() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(100,0,0));
        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(100,0,0))));
    }

    @Test
    public void testAddThreeRowStrangeOrder() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(2,0,0));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                3,1,1)));
    }

    @Test
    public void testAddThreeRowHarderStrangeOrder() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(2,0,0));
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(5,0,0));
        testSpace.add(new Position(4,0,0));
        testSpace.add(new Position(3,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                6,1,1)));
    }

    @Test
    public void testAddThreeRow() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,2,0));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,3,1)));
    }

    @Test
    public void testAddFourRow() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,2,0));
        testSpace.add(new Position(0,3,0));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,4,1)));
    }

    @Test
    public void testAddEightRow() {
        testSpace.add(new Position(0,0,1));
        testSpace.add(new Position(0,0,2));
        testSpace.add(new Position(0,0,3));
        testSpace.add(new Position(0,0,4));
        testSpace.add(new Position(0,0,5));
        testSpace.add(new Position(0,0,6));
        testSpace.add(new Position(0,0,7));
        testSpace.add(new Position(0,0,8));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                1,1,8)));
    }

    @Test
    public void testAddManyRow() {
        for (int i = 0; i < MAX_POSITIONS; i++) {
            testSpace.add(new Position(i, 0, 0));
        }
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS,1,1)));
    }

    @Test
    public void testAddCube() {
        buildUnitCube(testSpace);
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,2,2)));
    }

    @Test
    public void testAddManyCube() {
        Helpers.buildCube(testSpace, MAX_POSITIONS_CUBE);

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_CUBE,
                MAX_POSITIONS_CUBE,
                MAX_POSITIONS_CUBE)));
    }

    @Test
    public void testAddManyCubeTwice() {
        Helpers.buildCube(testSpace, MAX_POSITIONS_CUBE);
        Helpers.buildCube(testSpace, MAX_POSITIONS_CUBE * 2);

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_CUBE * 2,
                MAX_POSITIONS_CUBE * 2,
                MAX_POSITIONS_CUBE * 2)));
    }

    @Test
    public void testAddManyParallelepiped() {
        buildParallelepiped(testSpace);
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z)));
    }

    @Test
    public void testAddSetProperty() {
        Set<Position> testSet = new HashSet<>();
        for (int i = 0; i < MAX_POSITIONS; i++) {
            testSpace.add(new Position(0,0,0));
            testSet.add(new Position(0,0,0));

            assertEquals(testSpace.getVolume(), testSet.size());
        }
    }

    @Test
    public void testAddNeighboursDoNotMerge() {
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(1,1,0));
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(2,0,0));

        assertEquals(2, testSpace.size());
        assertTrue(testSpace.contains(new Position(0,1,0)));
        assertTrue(testSpace.contains(new Position(1,1,0)));
        assertTrue(testSpace.contains(new Position(1,0,0)));
        assertTrue(testSpace.contains(new Position(2,0,0)));
    }

    // REMOVE

    @Test
    public void testRemoveNothing() {
        testSpace.remove(Position.ORIGIN);

        assertTrue(testSpace.isEmpty());
        assertEquals(0, testSpace.size());
    }

    @Test
    public void testSimpleRemove() {
        testSpace.add(Position.ORIGIN);
        testSpace.remove(Position.ORIGIN);

        assertTrue(testSpace.isEmpty());
        assertEquals(0, testSpace.size());
    }

    @Test
    public void testSimpleRemoveTwoThings() {
        testSpace.add(Position.ORIGIN);
        testSpace.add(new Position(12, 2, 3));
        testSpace.remove(Position.ORIGIN);

        assertFalse(testSpace.isEmpty());
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(12, 2, 3))));
    }

    @Test
    public void testRemoveTwoX() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,0,0));

        testSpace.remove(new Position(1,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0, 0, 0))));
    }

    @Test
    public void testRemoveTwoY() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));

        testSpace.remove(new Position(0,1,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0, 0, 0))));
    }

    @Test
    public void testRemoveTwoZ() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,0,1));

        testSpace.remove(new Position(0,0,1));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0, 0, 0))));
    }

    @Test
    public void testRemoveSliceThroughEightRowX() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(2,0,0));
        testSpace.add(new Position(3,0,0));
        testSpace.add(new Position(4,0,0));
        testSpace.add(new Position(5,0,0));
        testSpace.add(new Position(6,0,0));
        testSpace.add(new Position(7,0,0));
        testSpace.add(new Position(8,0,0));

        testSpace.remove(new Position(4,0,0));
        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(1,0,0),
                3,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(5,0,0),
                4,1,1)));
    }

    @Test
    public void testRemoveSliceThroughEightRowY() {
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,2,0));
        testSpace.add(new Position(0,3,0));
        testSpace.add(new Position(0,4,0));
        testSpace.add(new Position(0,5,0));
        testSpace.add(new Position(0,6,0));
        testSpace.add(new Position(0,7,0));
        testSpace.add(new Position(0,8,0));

        testSpace.remove(new Position(0,4,0));
        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,1,0),
                1,3,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,5,0),
                1,4,1)));
    }

    @Test
    public void testRemoveSliceThroughEightRowZ() {
        testSpace.add(new Position(0,0,1));
        testSpace.add(new Position(0,0,2));
        testSpace.add(new Position(0,0,3));
        testSpace.add(new Position(0,0,4));
        testSpace.add(new Position(0,0,5));
        testSpace.add(new Position(0,0,6));
        testSpace.add(new Position(0,0,7));
        testSpace.add(new Position(0,0,8));

        testSpace.remove(new Position(0,0,4));
        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                1,1,3)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,5),
                1,1,4)));
    }

    @Test
    public void testRemoveSliceThroughCube() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    testSpace.add(new Position(x, y, z));
                }
            }
        }

        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                testSpace.remove(new Position(x, 1, z));
            }
        }

        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                3, 1, 3)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,2,0),
                3, 1, 3)));
    }

    @Test
    public void testRemoveSliceThroughBigParallelepiped() {
        buildParallelepiped(testSpace);
        for (int x = 0; x < Helpers.MAX_POSITIONS_X; x++) {
            for (int y = 0; y < Helpers.MAX_POSITIONS_Y; y++) {
                testSpace.remove(new Position(x, y, Helpers.MAX_POSITIONS_Z/2));
            }
        }

        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0, 0, 0),
                Helpers.MAX_POSITIONS_X,
                Helpers.MAX_POSITIONS_Y,
                Helpers.MAX_POSITIONS_Z/2)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(
                0,
                0,
                Helpers.MAX_POSITIONS_Z/2 + 1),
                Helpers.MAX_POSITIONS_X,
                Helpers.MAX_POSITIONS_Y,
                Helpers.MAX_POSITIONS_Z/2)));
    }
}
