package util;

import org.junit.Test;
import static org.junit.Assert.*;

public class MathTest {
    @Test
    public void testLessThanSum() {
        assertTrue(Math.lessThanSum(1, -123, 3000));
        assertTrue(Math.lessThanSum(1, 2, 3));
        assertTrue(Math.lessThanSum(1, 1, 1));
        assertTrue(Math.lessThanSum(1, 2, 0));
        assertTrue(Math.lessThanSum(1, 0, 2));
        assertTrue(Math.lessThanSum(1, Integer.MAX_VALUE, 3));
        assertTrue(Math.lessThanSum(1, Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertTrue(Math.lessThanSum(1, Integer.MAX_VALUE, -123124));

        assertFalse(Math.lessThanSum(1, 0, 1));
        assertFalse(Math.lessThanSum(1, 1, 0));
    }

    @Test
    public void testLessThanSumNegativeNumber() {
        assertTrue(Math.lessThanSum(-10, -123, 120));
        assertTrue(Math.lessThanSum(-10, 2, 3));
        assertTrue(Math.lessThanSum(-10, 1, 1));
        assertTrue(Math.lessThanSum(-10, 2, 0));
        assertTrue(Math.lessThanSum(-10, 0, 2));
        assertTrue(Math.lessThanSum(-10, Integer.MAX_VALUE, 3));
        assertTrue(Math.lessThanSum(-10, Integer.MAX_VALUE, -123124));

        assertFalse(Math.lessThanSum(-10, 0, -10));
        assertFalse(Math.lessThanSum(-10, -10, 0));
    }
}
