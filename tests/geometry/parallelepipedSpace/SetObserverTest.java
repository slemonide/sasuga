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
import static geometry.parallelepipedSpace.ParallelepipedSpaceTestHelpers.buildParallelepiped;
import static geometry.parallelepipedSpace.ParallelepipedSpaceTestHelpers.buildUnitCube;
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
        ParallelepipedSpaceTestHelpers.cutParallelepipedBottom(testSpace);

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        assertTrue(difference.getRemoved().contains(new Parallelepiped(new Position(0,0,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z)));
        assertEquals(1, difference.getAdded().size());
        assertTrue(difference.getAdded().contains(new Parallelepiped(new Position(0,0,1),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z - 1)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,1),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z - 1)));
    }

    @Test
    public void testRemoveAddBottom() {
        buildParallelepiped(testSpace);
        parallelepipedSpaceObserver.getDifference();
        ParallelepipedSpaceTestHelpers.cutParallelepipedTop(testSpace);

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        assertTrue(difference.getRemoved().contains(new Parallelepiped(new Position(0,0,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z)));
        assertEquals(1, difference.getAdded().size());
        assertTrue(difference.getAdded().contains(new Parallelepiped(new Position(0,0,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z - 1)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z - 1)));
    }

    @Test
    public void testRemoveAddRight() {
        buildParallelepiped(testSpace);
        parallelepipedSpaceObserver.getDifference();
        ParallelepipedSpaceTestHelpers.cutParallelepipedLeft(testSpace);

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        assertTrue(difference.getRemoved().contains(new Parallelepiped(new Position(0,0,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z)));
        assertEquals(1, difference.getAdded().size());
        assertTrue(difference.getAdded().contains(new Parallelepiped(new Position(0,0,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y - 1, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y - 1, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z)));
    }

    @Test
    public void testRemoveAddLeft() {
        buildParallelepiped(testSpace);
        parallelepipedSpaceObserver.getDifference();
        ParallelepipedSpaceTestHelpers.cutParallelepipedRight(testSpace);

        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();
        assertEquals(1, difference.getRemoved().size());
        assertTrue(difference.getRemoved().contains(new Parallelepiped(new Position(0,0,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z)));
        assertEquals(1, difference.getAdded().size());
        assertTrue(difference.getAdded().contains(new Parallelepiped(new Position(0,1,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y - 1, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z)));

        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,1,0),
                ParallelepipedSpaceTestHelpers.MAX_POSITIONS_X, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Y - 1, ParallelepipedSpaceTestHelpers.MAX_POSITIONS_Z)));
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
        buildUnitCube(testSpace);
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
}
