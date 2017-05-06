package util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DifferenceTest {
    @Test
    public void testConstructor() {
        Difference<Integer> difference = new Difference<>(1, 2);

        assertEquals(new Integer("1"), difference.getAdded());
        assertEquals(new Integer("2"), difference.getRemoved());
    }
}
