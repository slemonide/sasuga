package geometry;

import org.junit.Test;
import static org.junit.Assert.*;

public class IntegerIntervalTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidLengthException() {
        new IntegerInterval(0, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidLengthExceptionBorderCase() {
        new IntegerInterval(-2, 0);
    }

    @Test
    public void testNoIntersectionBorderCaseRight() {
        IntegerInterval intervalA = new IntegerInterval(0, 10);
        IntegerInterval intervalB = new IntegerInterval(10, 20);

        assertFalse(intervalA.intersects(intervalB));
        assertFalse(intervalB.intersects(intervalA));
    }

    @Test
    public void testNoIntersectionBorderCaseLeft() {
        IntegerInterval intervalA = new IntegerInterval(0, 30);
        IntegerInterval intervalB = new IntegerInterval(-10, 10);

        assertFalse(intervalA.intersects(intervalB));
        assertFalse(intervalB.intersects(intervalA));
    }

    @Test
    public void testNoIntersectionGeneralCase() {
        IntegerInterval intervalA = new IntegerInterval(-100, 10);
        IntegerInterval intervalB = new IntegerInterval(20, 30);

        assertFalse(intervalA.intersects(intervalB));
        assertFalse(intervalB.intersects(intervalA));
    }

    @Test
    public void testIntersectsSameInterval() {
        IntegerInterval intervalA = new IntegerInterval(20, 30);
        IntegerInterval intervalB = new IntegerInterval(20, 30);

        assertTrue(intervalA.intersects(intervalB));
        assertTrue(intervalB.intersects(intervalA));
    }

    @Test
    public void testIntersectsSubset() {
        IntegerInterval intervalA = new IntegerInterval(20, 30);
        IntegerInterval intervalB = new IntegerInterval(25, 5);

        assertTrue(intervalA.intersects(intervalB));
        assertTrue(intervalB.intersects(intervalA));
    }

    @Test
    public void testIntersectsOneElementIntersectionRight() {
        IntegerInterval intervalA = new IntegerInterval(0, 30);
        IntegerInterval intervalB = new IntegerInterval(29, 30);

        assertTrue(intervalA.intersects(intervalB));
        assertTrue(intervalB.intersects(intervalA));
    }

    @Test
    public void testIntersectsOneElementIntersectionLeft() {
        IntegerInterval intervalA = new IntegerInterval(0, 30);
        IntegerInterval intervalB = new IntegerInterval(-10, 11);

        assertTrue(intervalA.intersects(intervalB));
        assertTrue(intervalB.intersects(intervalA));
    }

    @Test
    public void testIntersectsManyElementIntersection() {
        IntegerInterval intervalA = new IntegerInterval(0, 30);
        IntegerInterval intervalB = new IntegerInterval(10, 100);

        assertTrue(intervalA.intersects(intervalB));
        assertTrue(intervalB.intersects(intervalA));
    }
}
