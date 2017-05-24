package geometry;

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
     * If the given position is not in the set, add it. Otherwise, do nothing.
     * @param position new position to be added
     */
    public void add(Position position) {
        if (this.contains(position)) {
            return;
        }

        add(new Parallelepiped(position));

        hasValidState();
    }

    /**
     * If the given parallelepiped is not in the set, add it. Otherwise, do nothing.
     * <p>It's expected that the given parallelepiped does not intersect with any already existing parallelepipeds</p>
     * @param parallelepiped new position to be added
     */
    public void add(@NotNull Parallelepiped parallelepiped) {
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

        Position newCorner = Position.min(parallelepipedA.getCorner(), parallelepipedB.getCorner());

        Parallelepiped newParallelepiped = parallelepipedA.setCorner(newCorner).setSize(axis,
                        parallelepipedB.getSize(axis) + parallelepipedA.getSize(axis));

        add(newParallelepiped);

        hasValidState();
    }

    /**
     * Produces true if this space contains given position, false otherwise
     * <p>Iterates through all parallelepipeds in the set to check if they
     * contain the given position.</p>
     * @param position position to check
     * @return true if this space contains given position, false otherwise
     */
    public boolean contains(@NotNull Position position) {
        return (get(position) != null);
    }

    public void remove(Position position) {
        if (contains(position)) {
            Parallelepiped toSplit = get(position);
            assert toSplit != null;

            parallelepipeds.remove(toSplit);
            if (toSplit.getVolume() != 1) {
                addBottom(position, toSplit);
                addTop(position, toSplit);
                addRight(position, toSplit);
                addLeft(position, toSplit);
                addFront(position, toSplit);
                addBack(position, toSplit);
            }
        }

        hasValidState();
    }

    private void addBack(Position position, Parallelepiped toSplit) {
        if (position.x < toSplit.getCorner().x + toSplit.getSize(X) - 1) {
            add(toSplit.setSize(Z, 1).setSize(Y, 1)
            .setSize(X, toSplit.getCorner().x + toSplit.getSize(X) - 1 - position.x)
                    .setCorner(toSplit.getCorner().set(Z, position.z)
                            .set(Y, position.y).set(X, position.x + 1)));
        }

        hasValidState();
    }

    private void addFront(Position position, Parallelepiped toSplit) {
        if (position.x > toSplit.getCorner().x) {
            add(toSplit.setSize(Z, 1).setSize(Y, 1)
            .setSize(X, position.x - toSplit.getCorner().x)
            .setCorner(toSplit.getCorner().set(Z, position.z)
            .set(Y, position.y)));
        }

        hasValidState();
    }

    private void addLeft(Position position, Parallelepiped toSplit) {
        if (position.y < toSplit.getCorner().y + toSplit.getSize(Y) - 1) {
            add(toSplit.setSize(Z, 1)
            .setSize(Y, toSplit.getCorner().y + toSplit.getSize(Y) - 1 - position.y)
            .setCorner(toSplit.getCorner().set(Z, position.z).set(Y, position.y + 1)));
        }

        hasValidState();
    }

    private void addRight(Position position, Parallelepiped toSplit) {
        if (position.y > toSplit.getCorner().y) {
            add(toSplit.setSize(Z, 1)
                    .setSize(Y, position.y - toSplit.getCorner().y)
            .setCorner(toSplit.getCorner().set(Z, position.z)));
        }

        hasValidState();
    }

    private void addTop(Position position, Parallelepiped toSplit) {
        if (position.z < toSplit.getCorner().z + toSplit.getSize(Z) - 1) {
            add(toSplit.setSize(Z, toSplit.getCorner().z + toSplit.getSize(Z) - 1 - position.z)
            .setCorner(toSplit.getCorner().set(Z, position.z + 1)));
        }

        hasValidState();
    }

    private void addBottom(Position position, Parallelepiped toSplit) {
        if (position.z > toSplit.getCorner().z) {
            add(toSplit.setSize(Z, position.z - toSplit.getCorner().z));
        }

        hasValidState();
    }

    /**
     * Return a parallelepiped containing the voxel with the given position
     * If there is no match, return null
     */
    public Parallelepiped get(Position position) {
        for (Parallelepiped parallelepiped : parallelepipeds) {
            if (parallelepiped.contains(position)) {
                return parallelepiped;
            }
        }
        return null;
    }

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
            volumeSoFar += parallelepiped.getVolume();
        }

        return volumeSoFar;
    }

    /**
     * Check invariant
     */
    private void hasValidState() {
        assert !parallelepipedsIntersect();
    }

    private boolean parallelepipedsIntersect() {
        for (Parallelepiped parallelepiped1 : parallelepipeds) {
            for (Parallelepiped parallelepiped2 : parallelepipeds) {
                if (parallelepiped1 != parallelepiped2) {
                    if (parallelepiped1.intersects(parallelepiped2)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void remove(Parallelepiped parallelepiped) {
        // TODO: add a faster implementations

        parallelepiped.positions().forEach(this::remove);
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
        return parallelepipeds.stream().flatMap(Parallelepiped::positions);
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
}
