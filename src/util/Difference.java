package util;

/**
 * Represents a difference in some data
 */
public class Difference<E> {
    private final E added;
    private final E removed;

    public Difference(E added, E removed) {
        this.added = added;
        this.removed = removed;
    }

    public E getAdded() {
        return added;
    }

    public E getRemoved() {
        return removed;
    }
}
