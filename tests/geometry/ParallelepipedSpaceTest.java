package geometry;

import model.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

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
        assertTrue(testSpace.getToRemove().isEmpty());
        assertTrue(testSpace.getToAdd().isEmpty());
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
        buildUnitCube();
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,2,2)));
    }

    private void buildUnitCube() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,1,0));
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(1,0,1));
        testSpace.add(new Position(0,0,1));
        testSpace.add(new Position(1,1,1));
        testSpace.add(new Position(0,1,1));
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
                testSpace.add(new Position(x, 1, z));
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
                MAX_POSITIONS_Z/2)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(
                0,
                0,
                MAX_POSITIONS_Z/2 + 1),
                MAX_POSITIONS_X,
                MAX_POSITIONS_Y,
                MAX_POSITIONS_Z/2)));
    }

    // toAdd

    @Test
    public void testToAddSimple() {
        testSpace.add(new Position(0,0,0));
        assertTrue(testSpace.getToRemove().isEmpty());
        Set<Parallelepiped> toAdd = testSpace.getToAdd();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testToAddTwo() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        assertTrue(testSpace.getToRemove().isEmpty());
        Set<Parallelepiped> toAdd = testSpace.getToAdd();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
    }

    @Test
    public void testToAddThree() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,2,0));
        assertTrue(testSpace.getToRemove().isEmpty());
        Set<Parallelepiped> toAdd = testSpace.getToAdd();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 3, 1)));
    }

    @Test
    public void testToAddMany() {
        for (int i = 0; i < MAX_POSITIONS; i++) {
            testSpace.add(new Position(0, i, 0));
        }
        assertTrue(testSpace.getToRemove().isEmpty());
        Set<Parallelepiped> toAdd = testSpace.getToAdd();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0),
                1, MAX_POSITIONS, 1)));
    }

    @Test
    public void testToAddCube() {
        buildUnitCube();
        assertTrue(testSpace.getToRemove().isEmpty());
        Set<Parallelepiped> toAdd = testSpace.getToAdd();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0),
                2, 2, 2)));
    }

    @Test
    public void testToAddSeveralInvocations() {
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.getToAdd().size());
        testSpace.add(new Position(0,0,0));
        assertEquals(0, testSpace.getToAdd().size());
        testSpace.add(new Position(1,0,0));
        assertEquals(1, testSpace.getToAdd().size());
    }

    // toRemove

    @Test
    public void testToRemoveEmpty() {
        testSpace.remove(new Position(0,0,0));
        assertTrue(testSpace.getToRemove().isEmpty());
        assertTrue(testSpace.getToAdd().isEmpty());
    }

    @Test
    public void testToRemoveNoChange() {
        testSpace.add(new Position(0,0,0));
        testSpace.remove(new Position(0,0,0));
        assertTrue(testSpace.getToRemove().isEmpty());
        assertTrue(testSpace.getToAdd().isEmpty());
    }

    @Test
    public void testToRemoveOne() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        testSpace.remove(new Position(0,0,0));
        assertTrue(testSpace.getToRemove().isEmpty());
        Set<Parallelepiped> toAdd = testSpace.getToAdd();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,1,0))));
    }

    @Test
    public void testToRemoveAnotherOne() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        Set<Parallelepiped> toAdd = testSpace.getToAdd();
        testSpace.getToRemove();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
        testSpace.remove(new Position(0,0,0));
        Set<Parallelepiped> toRemove = testSpace.getToRemove();
        assertEquals(1, toRemove.size());
        assertTrue(toRemove.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
        toAdd = testSpace.getToAdd();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,1,0))));
    }

    @Test
    public void testToRemoveThree() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(2,0,0));
        Set<Parallelepiped> toAdd = testSpace.getToAdd();
        testSpace.getToRemove();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 3, 1, 1)));
        testSpace.remove(new Position(1,0,0));
        Set<Parallelepiped> toRemove = testSpace.getToRemove();
        assertEquals(1, toRemove.size());
        assertTrue(toRemove.contains(new Parallelepiped(new Position(0,0,0), 3, 1, 1)));
        toAdd = testSpace.getToAdd();
        assertEquals(2, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0))));
        assertTrue(toAdd.contains(new Parallelepiped(new Position(2,0,0))));
    }

    @Test
    public void testToRemoveSeveralInvocations() {
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.getToAdd().size());
        testSpace.getToRemove();
        testSpace.add(new Position(0,0,0));
        assertEquals(0, testSpace.getToAdd().size());
        testSpace.getToRemove();
        testSpace.add(new Position(1,0,0));
        assertEquals(1, testSpace.getToAdd().size());
        testSpace.getToRemove();

        testSpace.remove(new Position(0,0,0));
        assertEquals(1, testSpace.getToRemove().size());
        testSpace.remove(new Position(0,0,0));
        assertEquals(0, testSpace.getToRemove().size());
        testSpace.remove(new Position(1,0,0));
        assertEquals(1, testSpace.getToRemove().size());
    }
}