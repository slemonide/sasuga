package util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Helps calculating changes of observed sets
 */
public class SetObserver<E> implements CollectionObserver<E> {
    private Set<E> observedSet;
    private Set<E> lastSeenSet;

    public SetObserver(Set<E> observedSet) {
        this.observedSet = observedSet;

        lastSeenSet = new HashSet<>();
        lastSeenSet.addAll(observedSet);
    }

    @Override
    public Difference<Collection<E>> getDifference() {
        Set<E> added = new HashSet<>();
        Set<E> removed = new HashSet<>();

        added.addAll(observedSet);
        added.removeAll(lastSeenSet);

        removed.addAll(lastSeenSet);
        removed.removeAll(observedSet);

        return new Difference<>(added, removed);
    }
}
