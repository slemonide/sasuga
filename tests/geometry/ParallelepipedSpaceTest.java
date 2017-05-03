package geometry;

import model.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelepipedSpaceTest {
    private static final int MAX_POSITIONS = 8917;
    private static final int MAX_POSITIONS_X = 10;
    private static final int MAX_POSITIONS_Y = 20;
    private static final int MAX_POSITIONS_Z = 17;
    private static final int MAX_POSITIONS_CUBE = 17;
    private ParallelepipedSpace testSpace;

    @Before
    public void runBefore() {
        testSpace = new ParallelepipedSpace();
    }

    @Test
    public void testConstructor() {
        assertTrue(testSpace.isEmpty());
        assertEquals(0, testSpace.size());
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
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,1,0));
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(1,0,1));
        testSpace.add(new Position(0,0,1));
        testSpace.add(new Position(1,1,1));
        testSpace.add(new Position(0,1,1));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,2,2)));
    }

    @Test
    public void testAddManyCube() {
        for (int x = 0; x < MAX_POSITIONS_CUBE; x++) {
            for (int y = 0; y < MAX_POSITIONS_CUBE; y++) {
                for (int z = 0; z < MAX_POSITIONS_CUBE; z++) {
                    testSpace.add(new Position(x, y, z));
                }
            }
        }
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_CUBE,
                MAX_POSITIONS_CUBE,
                MAX_POSITIONS_CUBE)));
    }

    @Test
    public void testAddManyParallelepiped() {
        buildParallelepiped();
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X,MAX_POSITIONS_Y,MAX_POSITIONS_Z)));
    }

    private void buildParallelepiped() {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int y = 0; y < MAX_POSITIONS_Y; y++) {
                for (int z = 0; z < MAX_POSITIONS_Z; z++) {
                    testSpace.add(new Position(x, y, z));
                }
            }
        }
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
    public void testRemoveSliceThroughBigParallelepiped() {
        buildParallelepiped();
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int y = 0; y < MAX_POSITIONS_Y; y++) {
                testSpace.remove(new Position(x, y, MAX_POSITIONS_Z/2));
            }
        }

        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0, 0, 0),
                MAX_POSITIONS_X,
                MAX_POSITIONS_Y,
                MAX_POSITIONS_Z/2 - 1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(
                0,
                0,
                MAX_POSITIONS_Z/2 + 1),
                MAX_POSITIONS_X,
                MAX_POSITIONS_Y,
                MAX_POSITIONS_Z/2)));
    }
}
