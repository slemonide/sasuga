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
        assertTrue(testSet.isEmpty());
        Difference<Integer> difference = testSetObserver.getDifference();

        assertTrue(difference.added.isEmpty());
        assertTrue(difference.removed.isEmpty());
    }

    @Test
    public void testConstructorHard() {
        testSet.add(1);
        testSet.add(2);
        testSetObserver = new SetObserver<>(testSet);

        assertEquals(2, testSet.size());
        Difference<Integer> difference = testSetObserver.getDifference();

        assertTrue(difference.added.isEmpty());
        assertTrue(difference.removed.isEmpty());
    }

    @Test
    public void testAdd() {
        testSet.add(1);
        testSet.add(2);
        testSet.add(3);

        Difference<Integer> difference = testSetObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        assertEquals(3, difference.added.size());
    }

    @Test
    public void testAddRemove() {
        testSet.add(1);
        testSet.remove(1);

        Difference<Integer> difference = testSetObserver.getDifference();

        assertTrue(difference.removed.isEmpty());
        assertTrue(difference.added.isEmpty());
    }
}
