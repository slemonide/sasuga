package util;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DifferenceTest {
    @Test
    public void testConstructor() {
        Map<Integer, Difference.UnaryFunction<Integer>> changeMap = new HashMap<>();
        changeMap.put(100, -> e + 1);
        changeMap.put(200, e -> e * e);

        Difference<Integer> testDifference = new Difference<>(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                changeMap);

        assertEquals(Arrays.asList(1,2,3), testDifference.added);
        assertEquals(Arrays.asList(4,5,6), testDifference.removed);
        assertEquals(changeMap, testDifference.changed);

        assertTrue(4 * 4 == changeMap.get(200).apply(4));
        assertTrue(4 + 1 == changeMap.get(100).apply(4));
    }
}
