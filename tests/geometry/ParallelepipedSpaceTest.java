package geometry;

import model.Position;
import model.RandomWalkCell;
import org.junit.Before;
import org.junit.Test;
import util.Difference;
import util.SetObserver;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

public class ParallelepipedSpaceTest {
    private static final int MAX_POSITIONS = 8917;
    private static final int MAX_POSITIONS_X = 10;
    private static final int MAX_POSITIONS_Y = 20;
    private static final int MAX_POSITIONS_Z = 17;
    private static final int MAX_POSITIONS_CUBE = 17;
    private static final int MAX_STEPS_RANDOMWALK = 1000;
    private static final int MAX_POSITIONS_RANDOM_FILL = 1000;
    private static final int RANDOM_FILL_RADIUS = 9;
    private ParallelepipedSpace testSpace;
    private SetObserver<Parallelepiped> parallelepipedSpaceObserver;

    @Before
    public void runBefore() {
        testSpace = new ParallelepipedSpace();
        parallelepipedSpaceObserver = new SetObserver<>(testSpace.getParallelepipeds());
    }

    @Test
    public void testConstructor() {
        assertTrue(testSpace.isEmpty());
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        assertTrue(difference.getAdded().isEmpty());
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

    @Test
    public void testAddSetProperty() {
        Set<Position> testSet = new HashSet<>();
        for (int i = 0; i < MAX_POSITIONS; i++) {
            testSpace.add(new Position(0,0,0));
            testSet.add(new Position(0,0,0));

            assertEquals(testSpace.getVolume(), testSet.size());
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



    @Test
    public void testRemoveCutBottomAddTop() {
        buildParallelepiped();
        parallelepipedSpaceObserver.getDifference();
        cutParallelepipedBottom();

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        assertTrue(difference.getRemoved().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y, MAX_POSITIONS_Z)));
        assertEquals(1, difference.getAdded().size());
        assertTrue(difference.getAdded().contains(new Parallelepiped(new Position(0,0,1),
                MAX_POSITIONS_X, MAX_POSITIONS_Y, MAX_POSITIONS_Z - 1)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                MAX_POSITIONS_X, MAX_POSITIONS_Y, MAX_POSITIONS_Z - 1)));
    }

    @Test
    public void testRemoveAddBottom() {
        buildParallelepiped();
        parallelepipedSpaceObserver.getDifference();
        cutParallelepipedTop();

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        assertTrue(difference.getRemoved().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y, MAX_POSITIONS_Z)));
        assertEquals(1, difference.getAdded().size());
        assertTrue(difference.getAdded().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y, MAX_POSITIONS_Z - 1)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y, MAX_POSITIONS_Z - 1)));
    }

    @Test
    public void testRemoveAddRight() {
        buildParallelepiped();
        parallelepipedSpaceObserver.getDifference();
        cutParallelepipedLeft();

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        assertTrue(difference.getRemoved().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y, MAX_POSITIONS_Z)));
        assertEquals(1, difference.getAdded().size());
        assertTrue(difference.getAdded().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y - 1, MAX_POSITIONS_Z)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y - 1, MAX_POSITIONS_Z)));
    }

    @Test
    public void testRemoveAddLeft() {
        buildParallelepiped();
        parallelepipedSpaceObserver.getDifference();
        cutParallelepipedRight();

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        assertTrue(difference.getRemoved().contains(new Parallelepiped(new Position(0,0,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y, MAX_POSITIONS_Z)));
        assertEquals(1, difference.getAdded().size());
        assertTrue(difference.getAdded().contains(new Parallelepiped(new Position(0,1,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y - 1, MAX_POSITIONS_Z)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,1,0),
                MAX_POSITIONS_X, MAX_POSITIONS_Y - 1, MAX_POSITIONS_Z)));
    }

    private void cutParallelepipedRight() {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int z = 0; z < MAX_POSITIONS_Z; z++) {
                testSpace.remove(new Position(x, 0, z));
            }
        }
    }

    private void cutParallelepipedLeft() {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int z = 0; z < MAX_POSITIONS_Z; z++) {
                testSpace.remove(new Position(x, MAX_POSITIONS_Y - 1, z));
            }
        }
    }

    private void cutParallelepipedTop() {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int y = 0; y < MAX_POSITIONS_Y; y++) {
                testSpace.remove(new Position(x, y, MAX_POSITIONS_Z - 1));
            }
        }
    }

    private void cutParallelepipedBottom() {
        for (int x = 0; x < MAX_POSITIONS_X; x++) {
            for (int y = 0; y < MAX_POSITIONS_Y; y++) {
                testSpace.remove(new Position(x, y, 0));
            }
        }
    }

    // toAdd

    @Test
    public void testToAddSimple() {
        testSpace.add(new Position(0,0,0));

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        Collection<Parallelepiped> toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testToAddTwo() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        Collection<Parallelepiped> toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
    }

    @Test
    public void testToAddThree() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,2,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        Collection<Parallelepiped> toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 3, 1)));
    }

    @Test
    public void testToAddMany() {
        for (int i = 0; i < MAX_POSITIONS; i++) {
            testSpace.add(new Position(0, i, 0));
        }
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        Collection<Parallelepiped> toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0),
                1, MAX_POSITIONS, 1)));
    }

    @Test
    public void testToAddCube() {
        buildUnitCube();
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        Collection<Parallelepiped> toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0),
                2, 2, 2)));
    }

    @Test
    public void testToAddSeveralInvocations() {
        testSpace.add(new Position(0,0,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertEquals(1, difference.getAdded().size());
        testSpace.add(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();

        assertEquals(0, difference.getAdded().size());
        testSpace.add(new Position(1,0,0));
        difference = parallelepipedSpaceObserver.getDifference();

        assertEquals(1, difference.getAdded().size());
    }

    // toRemove

    @Test
    public void testToRemoveEmpty() {
        testSpace.remove(new Position(0,0,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertTrue(difference.getRemoved().isEmpty());
        assertTrue(difference.getAdded().isEmpty());
    }

    @Test
    public void testToRemoveNoChange() {
        testSpace.add(new Position(0,0,0));
        testSpace.remove(new Position(0,0,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        assertTrue(difference.getAdded().isEmpty());
    }

    @Test
    public void testToRemoveOne() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        testSpace.remove(new Position(0,0,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        Collection<Parallelepiped> toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,1,0))));
    }

    @Test
    public void testToRemoveAnotherOne() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        Collection<Parallelepiped> toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
        testSpace.remove(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();

        Collection<Parallelepiped> toRemove = difference.getRemoved();
        assertEquals(1, toRemove.size());
        assertTrue(toRemove.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
        toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,1,0))));
    }

    @Test
    public void testToRemoveThree() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(2,0,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        Collection<Parallelepiped> toAdd = difference.getAdded();
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 3, 1, 1)));
        testSpace.remove(new Position(1,0,0));
        difference = parallelepipedSpaceObserver.getDifference();

        Collection<Parallelepiped> toRemove = difference.getRemoved();
        assertEquals(1, toRemove.size());
        assertTrue(toRemove.contains(new Parallelepiped(new Position(0,0,0), 3, 1, 1)));
        toAdd = difference.getAdded();
        assertEquals(2, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0))));
        assertTrue(toAdd.contains(new Parallelepiped(new Position(2,0,0))));
    }

    @Test
    public void testToRemoveSeveralInvocations() {
        testSpace.add(new Position(0,0,0));
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        assertEquals(1, difference.getAdded().size());
        testSpace.add(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(0, difference.getAdded().size());
        testSpace.add(new Position(1,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getAdded().size());

        testSpace.remove(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        testSpace.remove(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(0, difference.getRemoved().size());
        testSpace.remove(new Position(1,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
    }

    @Test
    public void testGetVolumeBasic() {
        testSpace.add(new Position(0,0,0));

        assertEquals(1, testSpace.getVolume());
    }

    @Test
    public void testGetVolumeDouble() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,0,0));

        assertEquals(1, testSpace.getVolume());
    }

    @Test
    public void testGetVolumeThree() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,1,0));

        assertEquals(3, testSpace.getVolume());
    }

    @Test
    public void testGetVolumeHard() {
        buildParallelepiped();

        assertEquals(MAX_POSITIONS_X * MAX_POSITIONS_Y * MAX_POSITIONS_Z, testSpace.getVolume());
    }

    @Test
    public void testGetVolumeRandomFill() {
        Random random = new Random();

        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < MAX_POSITIONS_RANDOM_FILL; i++) {
            Position position = new Position(
                    random.nextInt(),
                    random.nextInt(),
                    random.nextInt());

            positions.add(position);
            testSpace.add(position);

            assertEquals(positions.size(), testSpace.getVolume());
        }
    }

    @Test
    public void testGetVolumeRandomFillConfined() {
        Random random = new Random();

        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < MAX_POSITIONS_RANDOM_FILL; i++) {
            Position position = new Position(
                    random.nextInt(RANDOM_FILL_RADIUS),
                    random.nextInt(RANDOM_FILL_RADIUS),
                    random.nextInt(RANDOM_FILL_RADIUS));

            positions.add(position);
            testSpace.add(position);

            assertEquals(positions.size(), testSpace.getVolume());
        }
    }

    @Test
    public void testGetVolumeRandomWalk() {
        Position currentPosition = new Position(0,0,0);

        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < MAX_STEPS_RANDOMWALK; i++) {
            positions.add(currentPosition);
            testSpace.add(currentPosition);

            assertEquals(positions.size(), testSpace.getVolume());

            currentPosition = currentPosition.add(RandomWalkCell.nextPosition());
        }
    }

    // Implementation-dependent tests

    @Test
    public void testRemoveCenterOfAnOddCube() {
        buildCube(3);
        assertEquals(1, testSpace.size());
        assertEquals(3 * 3 * 3, testSpace.getVolume());

        // remove center
        testSpace.remove(new Position(1,1,1));

        assertEquals(6, testSpace.getParallelepipeds().size());

        // Z-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                3,3,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,2),
                3,3,1)));

        // Y-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                3,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,2,1),
                3,1,1)));

        // X-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,1,1),
                1,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(2,1,1),
                1,1,1)));
    }

    @Test
    public void testRemoveCenterOfAnEvenCube() {
        buildCube(4);
        assertEquals(1, testSpace.size());
        assertEquals(4 * 4 * 4, testSpace.getVolume());

        // remove center
        testSpace.remove(new Position(1,1,1));

        assertEquals(6, testSpace.getParallelepipeds().size());

        // Z-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                4,4,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,2),
                4,4,2)));

        // Y-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                4,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,2,1),
                4,2,1)));

        // X-axis
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,1,1),
                1,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(2,1,1),
                2,1,1)));
    }

    private void buildCube(int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    testSpace.add(new Position(x, y, z));
                }
            }
        }
    }
}
