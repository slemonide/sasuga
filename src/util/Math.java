package util;

import org.jetbrains.annotations.Contract;

/**
 * Different static helper methods for math
 */
public class Math {

    /**
     * Produce true if number is less than the sum of a and b
     *
     * <p>
     *     This method accounts for possible buffer overflow
     * </p>
     * @param number a number to be checked
     * @param a first number in the sum
     * @param b second number in the sum
     * @return true if number is less than a + b, false otherwise
     */
    @Contract(pure = true)
    public static boolean lessThanSum(int number, int a, int b) throws IllegalArgumentException {
        if (a > 0 && b > 0 && (a + b < 0)) {
            // check for buffer overflow
            return true;
        } else {
            return number < a + b;
        }
    }
}
