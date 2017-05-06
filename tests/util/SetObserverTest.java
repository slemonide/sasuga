package util;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class SetObserverTest {
    private Set<Integer> testSet;
    private SetObserver<Integer> testSetObserver;

    @Before
    public void runBefore() {
        testSet = new HashSet<>();
        testSetObserver = new SetObserver<>(testSet);
    }

    @Test
    public void testConstructor() {
        // TODO: finish
    }

    @Test
    public void testAdd() {
        testSet.add(1);
        testSet.add(2);
        testSet.add(3);

        Difference<Collection<Integer>> difference = testSetObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        assertEquals(3, difference.getAdded().size());
    }

    @Test
    public void testAddRemove() {
        testSet.add(1);
        testSet.remove(1);

        Difference<Collection<Integer>> difference = testSetObserver.getDifference();

        assertTrue(difference.getRemoved().isEmpty());
        assertTrue(difference.getAdded().isEmpty());
    }
}
