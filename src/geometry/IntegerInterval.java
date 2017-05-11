package geometry;

/**
 * An integer interval
 */
public final class IntegerInterval {

    private final int start;
    private final int length;

    /**
     * Create an integer interval with the given starting value and length
     * @param start starting value
     * @param length length of the interval in the positive direction
     * @throws IllegalArgumentException if given length is negative or zero
     */
    public IntegerInterval(int start, int length) throws IllegalArgumentException {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }

        this.start = start;
        this.length = length;
    }

    /**
     * Produces true if this interval intersects other interval
     */
    public boolean intersects(IntegerInterval otherInterval) {
        int newMin = Math.max(this.start, otherInterval.start);
        int newMax = Math.min(this.start + this.length, otherInterval.start + otherInterval.length);

        return newMax > newMin;
    }
}
