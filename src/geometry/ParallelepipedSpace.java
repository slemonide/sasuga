package geometry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

import static geometry.Axis.X;
import static geometry.Axis.Y;
import static geometry.Axis.Z;

/**
 * Represents a set of parallelepipeds
 *
 * invariant
 *    no parallelepipeds contain the same block twice
 */
public class ParallelepipedSpace implements Iterable<Parallelepiped> {
    private Set<Parallelepiped> parallelepipeds;

    /**
     * Create an empty parallelepiped space
     */
    public ParallelepipedSpace() {
        parallelepipeds = new CopyOnWriteArraySet<>();  //= new HashSet<>();
    }

    /**
     * Create parallelepiped space that contains all of the parallelepipeds that
     * <tt>space</tt> contains
     * @param space parallelepiped space from which to copy parallelepipeds
     */
    public ParallelepipedSpace(@NotNull ParallelepipedSpace space) {
        parallelepipeds = new CopyOnWriteArraySet<>();
        parallelepipeds.addAll(space.parallelepipeds);
    }

    /**
     * If the given position is not in the set, add it. Otherwise, do nothing.
     * @param position new position to be added
     */
    public void add(@NotNull Position position) {
        if (this.contains(position)) {
            return;
        }

        add(new Parallelepiped(position));

        hasValidState();
    }

    /**
     * If the given parallelepiped is not in the set, add it. Otherwise, do nothing.
     * @param parallelepiped new position to be added
     */
    public void add(@NotNull Parallelepiped parallelepiped) {
        // TODO: make this more robust
        assert (!parallelepipeds.contains(parallelepiped));

        for (Axis axis : Axis.values()) {
            for (Parallelepiped neighbour : parallelepiped.getInterlockingNeighbours(this, axis)) {
                assert (!neighbour.intersects(parallelepiped));

                if (parallelepiped.fits(axis, neighbour)) {
                    mergeParallelepipeds(parallelepiped, axis, neighbour);
                    return;
                }
            }
        }

        parallelepipeds.add(parallelepiped);

        hasValidState();
    }

    /**
     * Remove the given parallelepipeds from the set; merge them and add the new parallelepiped to the set
     * @param parallelepipedA first parallelepiped
     * @param axis axis of the adjacent side of the given parallelepipeds
     * @param parallelepipedB second parallelepiped
     */
    private void mergeParallelepipeds(@NotNull Parallelepiped parallelepipedA,
                                      @NotNull Axis axis,
                                      @NotNull Parallelepiped parallelepipedB) {

        parallelepipeds.remove(parallelepipedB);
        parallelepipeds.remove(parallelepipedA);

        Position newCorner = parallelepipedA.getCorner().min(parallelepipedB.getCorner());

        Parallelepiped newParallelepiped = parallelepipedA.setCorner(newCorner).setSize(axis,
                        parallelepipedB.getSize(axis) + parallelepipedA.getSize(axis));

        add(newParallelepiped);

        hasValidState();
    }

    /**
     * Produces true if this space contains given position, false otherwise
     * <p>
     *     Iterates through all parallelepipeds in the set to check if they
     *     contain the given position.
     * </p>
     * @param position position to check
     * @return true if this space contains given position, false otherwise
     */
    public boolean contains(@NotNull Position position) {
        return (get(position).isPresent());
    }

    /**
     * Produces true if this space contains given parallelepiped, false otherwise
     * <p>
     *     Space contains given parallelepiped either if it is present in the collection,
     *     or if it can be fully fit inside a number of present parallelepipeds
     * </p>
     * @param parallelepiped parallelepiped to search for
     * @return true if this space contains given parallelepiped, false otherwise
     */
    public boolean contains(Parallelepiped parallelepiped) {
        // TODO: cover all cases
        return parallelepipeds.contains(parallelepiped);
    }

    /**
     * Remove the given position from this space
     * @param position position to be removed
     */
    public void remove(@NotNull Position position) {
        get(position).ifPresent(toSplit -> {
            parallelepipeds.remove(toSplit);
            if (toSplit.volume() != 1) {
                addBottom(position, toSplit);
                addTop(position, toSplit);
                addRight(position, toSplit);
                addLeft(position, toSplit);
                addFront(position, toSplit);
                addBack(position, toSplit);
            }
        });

        hasValidState();
    }

    private void addBack(@NotNull Position position,
                         @NotNull Parallelepiped toSplit) {
        if (position.x < toSplit.getCorner().x + toSplit.getSize(X) - 1) {
            add(toSplit.setSize(Z, 1).setSize(Y, 1)
            .setSize(X, toSplit.getCorner().x + toSplit.getSize(X) - 1 - position.x)
                    .setCorner(toSplit.getCorner().set(Z, position.z)
                            .set(Y, position.y).set(X, position.x + 1)));
        }

        hasValidState();
    }

    private void addFront(@NotNull Position position,
                          @NotNull Parallelepiped toSplit) {
        if (position.x > toSplit.getCorner().x) {
            add(toSplit.setSize(Z, 1).setSize(Y, 1)
            .setSize(X, position.x - toSplit.getCorner().x)
            .setCorner(toSplit.getCorner().set(Z, position.z)
            .set(Y, position.y)));
        }

        hasValidState();
    }

    private void addLeft(@NotNull Position position,
                         @NotNull Parallelepiped toSplit) {
        if (position.y < toSplit.getCorner().y + toSplit.getSize(Y) - 1) {
            add(toSplit.setSize(Z, 1)
            .setSize(Y, toSplit.getCorner().y + toSplit.getSize(Y) - 1 - position.y)
            .setCorner(toSplit.getCorner().set(Z, position.z).set(Y, position.y + 1)));
        }

        hasValidState();
    }

    private void addRight(@NotNull Position position,
                          @NotNull Parallelepiped toSplit) {
        if (position.y > toSplit.getCorner().y) {
            add(toSplit.setSize(Z, 1)
                    .setSize(Y, position.y - toSplit.getCorner().y)
            .setCorner(toSplit.getCorner().set(Z, position.z)));
        }

        hasValidState();
    }

    private void addTop(@NotNull Position position,
                        @NotNull Parallelepiped toSplit) {
        if (position.z < toSplit.getCorner().z + toSplit.getSize(Z) - 1) {
            add(toSplit.setSize(Z, toSplit.getCorner().z + toSplit.getSize(Z) - 1 - position.z)
            .setCorner(toSplit.getCorner().set(Z, position.z + 1)));
        }

        hasValidState();
    }

    private void addBottom(@NotNull Position position,
                           @NotNull Parallelepiped toSplit) {
        if (position.z > toSplit.getCorner().z) {
            add(toSplit.setSize(Z, position.z - toSplit.getCorner().z));
        }

        hasValidState();
    }

    /**
     * Maybe return a parallelepiped containing the voxel with the given position
     * @param position position to check
     * @return a parallelepiped if it exists, Optional.empty() otherwise
     */
    @NotNull
    public Optional<Parallelepiped> get(@NotNull Position position) {
        for (Parallelepiped parallelepiped : parallelepipeds) {
            if (parallelepiped.contains(position)) {
                return Optional.of(parallelepiped);
            }
        }
        return Optional.empty();
    }

    @NotNull
    public Set<Parallelepiped> getParallelepipeds() {
        return Collections.unmodifiableSet(parallelepipeds);
    }

    /**
     * Produce the number of parallelepipeds in this set
     * @return number of parallelepipeds in this set
     */
    public int size() {
        return parallelepipeds.size();
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * @return <tt>true</tt> if this set contains no elements
     */
    public boolean isEmpty() {
        return parallelepipeds.isEmpty();
    }

    public long getVolume() {
        int volumeSoFar = 0;

        for (Parallelepiped parallelepiped : parallelepipeds) {
            volumeSoFar += parallelepiped.volume();
        }

        return volumeSoFar;
    }

    /**
     * Check invariant
     */
    private void hasValidState() {
        assert !parallelepipedsIntersect();
    }

    @Contract(pure = true)
    private boolean parallelepipedsIntersect() {
        for (Parallelepiped parallelepiped1 : parallelepipeds) {
            for (Parallelepiped parallelepiped2 : parallelepipeds) {
                if (parallelepiped1 != parallelepiped2 && parallelepiped1.intersects(parallelepiped2)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Entirely clears the space inside the given parallelepiped
     * @param parallelepiped a parallelepiped describing what space to clear
     */
    public void remove(Parallelepiped parallelepiped) {
        // TODO: add a faster implementations

        // TODO: finish
        // Also, this might be already (mostly) done in remove(Position)
        parallelepipeds.remove(parallelepiped);

        //parallelepiped.positions().forEach(this::remove);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<Parallelepiped> iterator() {
        return parallelepipeds.iterator();
    }

    /**
     * Produce the set of all positions in the storage
     *
     * @return set of all positions in the storage
     */
    @NotNull
    public Stream<Position> positions() {
        return parallelepipeds.parallelStream().flatMap(Parallelepiped::positions);
    }

    /**
     * Remove all the parallelepipeds in the given space from this space
     * @param toRemove parallelepipeds to be removed
     */
    public void removeAll(@NotNull ParallelepipedSpace toRemove) {
        for (Parallelepiped parallelepiped : toRemove) {
            remove(parallelepiped);
        }
    }

    /**
     * Add all the parallelepipeds from the given space to this space,
     * ignoring the ones that replace already placed ones
     *
     * <p>
     *    Parallelepipeds that clash with already existing ones are reduced and
     *    all parts that can be placed are placed
     * </p>
     * @param toAdd parallelepipeds to be added
     */
    public void addAll(@NotNull ParallelepipedSpace toAdd) {
        for (Parallelepiped parallelepiped : toAdd) {
            add(parallelepiped);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParallelepipedSpace that = (ParallelepipedSpace) o;

        if (parallelepipeds.equals(that.parallelepipeds)) {
            return true;
        } else {
            ParallelepipedSpace difference = new ParallelepipedSpace(this);
            difference.removeAll(that);

            return difference.isEmpty();
        }
    }

    @Override
    public int hashCode() {
        return ((int) getVolume() + 31 * getCenter().hashCode());
    }

    /**
     * Maybe produce the weighted center of this space
     *
     * <p>
     *     If this space is empty, produce nothing.
     *     Otherwise, produce the center
     * </p>
     * @return the weighted center of this space
     */
    public Optional<Position> getCenter() {
        if (parallelepipeds.isEmpty()) {
            return Optional.empty();
        } else {
            Position dividendSoFar = new Position(0,0,0);
            int divisorSoFar = 0;

            for (Parallelepiped parallelepiped : parallelepipeds) {
                dividendSoFar = dividendSoFar.add(parallelepiped.center().multiply(parallelepiped.volume()));
                divisorSoFar += parallelepiped.volume();
            }
            assert divisorSoFar != 0;

            Position center = dividendSoFar.divide(divisorSoFar);

            return Optional.of(center);
        }
    }

    /**
     * Remove all parallelepipeds from this space
     */
    public void clear() {
        parallelepipeds.clear();
    }
}
