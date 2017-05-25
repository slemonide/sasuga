package util;

/**
 * Helps calculating changes of observed collections
 */
public interface CollectionObserver<E> {
    Difference<E> getDifference();
}
