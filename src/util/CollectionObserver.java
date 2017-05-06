package util;

import java.util.Collection;

/**
 * Helps calculating changes of observed collections
 */
public interface CollectionObserver<E> {
    Difference<Collection<E>> getDifference();
}
