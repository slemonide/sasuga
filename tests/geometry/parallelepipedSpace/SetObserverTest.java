package geometry.parallelepipedSpace;

import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.junit.Before;
import org.junit.Test;
import util.Difference;
import util.SetObserver;

import java.util.Collection;

import static geometry.parallelepipedSpace.ParallelepipedSpaceTest.MAX_POSITIONS;
import static geometry.parallelepipedSpace.Helpers.buildParallelepiped;
import static geometry.parallelepipedSpace.Helpers.buildUnitCube;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Set observer integration test for the ParallelepipedSpace
 */
public class SetObserverTest {
    private ParallelepipedSpace testSpace;
    private SetObserver<Parallelepiped> parallelepipedSpaceObserver;

    @Before
    public void runBefore() {
        testSpace = new ParallelepipedSpace();
        parallelepipedSpaceObserver = new SetObserver<>(testSpace.getParallelepipeds());
    }

    @Test
    public void testRemoveCutBottomAddTop() {
        buildParallelepiped(testSpace);
        parallelepipedSpaceObserver.getDifference();
        Helpers.cutParallelepipedBottom(testSpace);

        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.removed.size());
        assertTrue(difference.removed.contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z)));
        assertEquals(1, difference.added.size());
        assertTrue(difference.added.contains(new Parallelepiped(new Position(0,0,1),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z - 1)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z - 1)));
    }

    @Test
    public void testRemoveAddBottom() {
        buildParallelepiped(testSpace);
        parallelepipedSpaceObserver.getDifference();
        Helpers.cutParallelepipedTop(testSpace);

        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.removed.size());
        assertTrue(difference.removed.contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z)));
        assertEquals(1, difference.added.size());
        assertTrue(difference.added.contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z - 1)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z - 1)));
    }

    @Test
    public void testRemoveAddRight() {
        buildParallelepiped(testSpace);
        parallelepipedSpaceObserver.getDifference();
        Helpers.cutParallelepipedLeft(testSpace);

        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.removed.size());
        assertTrue(difference.removed.contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z)));
        assertEquals(1, difference.added.size());
        assertTrue(difference.added.contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y - 1, Helpers.MAX_POSITIONS_Z)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y - 1, Helpers.MAX_POSITIONS_Z)));
    }

    @Test
    public void testRemoveAddLeft() {
        buildParallelepiped(testSpace);
        parallelepipedSpaceObserver.getDifference();
        Helpers.cutParallelepipedRight(testSpace);

        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.removed.size());
        assertTrue(difference.removed.contains(new Parallelepiped(new Position(0,0,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y, Helpers.MAX_POSITIONS_Z)));
        assertEquals(1, difference.added.size());
        assertTrue(difference.added.contains(new Parallelepiped(new Position(0,1,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y - 1, Helpers.MAX_POSITIONS_Z)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,1,0),
                Helpers.MAX_POSITIONS_X, Helpers.MAX_POSITIONS_Y - 1, Helpers.MAX_POSITIONS_Z)));
    }

    // toAdd

    @Test
    public void testToAddSimple() {
        testSpace.add(new Position(0,0,0));

        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        Collection<Parallelepiped> toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testToAddTwo() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        Collection<Parallelepiped> toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
    }

    @Test
    public void testToAddThree() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,2,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        Collection<Parallelepiped> toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 3, 1)));
    }

    @Test
    public void testToAddMany() {
        for (int i = 0; i < MAX_POSITIONS; i++) {
            testSpace.add(new Position(0, i, 0));
        }
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        Collection<Parallelepiped> toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0),
                1, MAX_POSITIONS, 1)));
    }

    @Test
    public void testToAddCube() {
        buildUnitCube(testSpace);
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        Collection<Parallelepiped> toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0),
                2, 2, 2)));
    }

    @Test
    public void testToAddSeveralInvocations() {
        testSpace.add(new Position(0,0,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertEquals(1, difference.added.size());
        testSpace.add(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();

        assertEquals(0, difference.added.size());
        testSpace.add(new Position(1,0,0));
        difference = parallelepipedSpaceObserver.getDifference();

        assertEquals(1, difference.added.size());
    }

    // toRemove

    @Test
    public void testToRemoveEmpty() {
        testSpace.remove(new Position(0,0,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();
        assertTrue(difference.removed.isEmpty());
        assertTrue(difference.added.isEmpty());
    }

    @Test
    public void testToRemoveNoChange() {
        testSpace.add(new Position(0,0,0));
        testSpace.remove(new Position(0,0,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        assertTrue(difference.added.isEmpty());
    }

    @Test
    public void testToRemoveOne() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        testSpace.remove(new Position(0,0,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        Collection<Parallelepiped> toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,1,0))));
    }

    @Test
    public void testToRemoveAnotherOne() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,1,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        Collection<Parallelepiped> toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
        testSpace.remove(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();

        Collection<Parallelepiped> toRemove = difference.removed;
        assertEquals(1, toRemove.size());
        assertTrue(toRemove.contains(new Parallelepiped(new Position(0,0,0), 1, 2, 1)));
        toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,1,0))));
    }

    @Test
    public void testToRemoveThree() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(2,0,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        Collection<Parallelepiped> toAdd = difference.added;
        assertEquals(1, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0), 3, 1, 1)));
        testSpace.remove(new Position(1,0,0));
        difference = parallelepipedSpaceObserver.getDifference();

        Collection<Parallelepiped> toRemove = difference.removed;
        assertEquals(1, toRemove.size());
        assertTrue(toRemove.contains(new Parallelepiped(new Position(0,0,0), 3, 1, 1)));
        toAdd = difference.added;
        assertEquals(2, toAdd.size());
        assertTrue(toAdd.contains(new Parallelepiped(new Position(0,0,0))));
        assertTrue(toAdd.contains(new Parallelepiped(new Position(2,0,0))));
    }

    @Test
    public void testToRemoveSeveralInvocations() {
        testSpace.add(new Position(0,0,0));
        Difference<Parallelepiped> difference = parallelepipedSpaceObserver.getDifference();

        assertEquals(1, difference.added.size());
        testSpace.add(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(0, difference.added.size());
        testSpace.add(new Position(1,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.added.size());

        testSpace.remove(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.removed.size());
        testSpace.remove(new Position(0,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(0, difference.removed.size());
        testSpace.remove(new Position(1,0,0));
        difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.removed.size());
    }
}
