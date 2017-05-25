package util;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a difference in a collection
 */
public final class Difference<E> {
    /**
     * Elements that were added to the collection
     */
    public final Collection<E> added;
    /**
     * Elements that were removed from the collection
     */
    public final Collection<E> removed;
    /**
     * Elements that were changed (but not removed or added)
     */
    final Map<E, UnaryFunction<E>> changed;

    public Difference(@NotNull Collection<E> added,
                      @NotNull Collection<E> removed,
                      @NotNull Map<E, UnaryFunction<E>> changed) {
        this.added = added;
        this.removed = removed;
        this.changed = changed;
    }
    /**
     * A function that transforms an old element to the new one
     * @param <E> Type of the data stored in the collection
     */
    public interface UnaryFunction<E> { E apply(E element); }
}
