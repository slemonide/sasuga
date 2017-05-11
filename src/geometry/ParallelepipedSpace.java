package geometry;

import model.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static geometry.Dimension.X;
import static geometry.Dimension.Y;
import static geometry.Dimension.Z;

/**
 * Represents a set of parallelepipeds
 *
 * invariant
 *   no parallelepipeds contain the same block twice
 */
public class ParallelepipedSpace {
    private Set<Parallelepiped> parallelepipeds;

    /**
     * Create an empty parallelepiped space
     */
    public ParallelepipedSpace() {
        parallelepipeds = new CopyOnWriteArraySet<>();  //= new HashSet<>();
    }

    public void add(Position position) {
        /*if (this.contains(position)) {
            return;
        }*/
        remove(position);

        add(new Parallelepiped(position));

        hasValidState();
    }

    private void add(@NotNull Parallelepiped parallelepiped) {
        Parallelepiped newParallelepiped = null;
        for (Dimension dimension : Dimension.values()) {

            Position unitVector = dimension.getUnitVector();
            Parallelepiped neighbour = get(parallelepiped.getCorner()
                    .add(unitVector.scale(parallelepiped.getSize(dimension))));
            if (neighbour != null) {

                if (parallelepipedsFit(parallelepiped, dimension, neighbour)) {
                    Position newCorner = parallelepiped.getCorner();

                    newParallelepiped = getParallelepiped(parallelepiped, dimension, neighbour, newCorner);
                }
            }

            if (newParallelepiped == null) {

                neighbour = get(parallelepiped.getCorner()
                        .add(unitVector.inverse()));
                if (neighbour != null) {

                    if (parallelepipedsFit(parallelepiped, dimension, neighbour)) {
                        Position newCorner = neighbour.getCorner();

                        newParallelepiped = getParallelepiped(parallelepiped, dimension, neighbour, newCorner);
                    }
                }
            }

            if (newParallelepiped != null) {
                break;
            }
        }

        if (newParallelepiped == null) {
            parallelepipeds.add(parallelepiped);
        } else {
            add(newParallelepiped);
        }

        hasValidState();
    }

    @NotNull
    private Parallelepiped getParallelepiped(@NotNull Parallelepiped parallelepiped, Dimension dimension, Parallelepiped neighbour, Position newCorner) {
        Parallelepiped newParallelepiped;
        parallelepipeds.remove(neighbour);

        newParallelepiped = neighbour
                .setCorner(newCorner).setSize(dimension,
                        neighbour.getSize(dimension) + parallelepiped.getSize(dimension));
        return newParallelepiped;
    }

    private boolean parallelepipedsFit(@NotNull Parallelepiped parallelepiped, Dimension dimension, Parallelepiped neighbour) {
        return neighbour.getSize(dimension.getComplements()[0])
                == parallelepiped.getSize(dimension.getComplements()[0])
                && neighbour.getSize(dimension.getComplements()[1])
                == parallelepiped.getSize(dimension.getComplements()[1]);
    }

    private boolean contains(@NotNull Position position) {
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
    private Parallelepiped get(Position position) {
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

    public int size() {
        return parallelepipeds.size();
    }

    public boolean isEmpty() {
        return parallelepipeds.isEmpty();
    }

    public int getVolume() {
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
        for (Parallelepiped parallelepiped1 : parallelepipeds) {
            for (Parallelepiped parallelepiped2 : parallelepipeds) {
                if (parallelepiped1 != parallelepiped2) {
                    assert (!parallelepiped1.intersects(parallelepiped2));
                }
            }
        }
    }
}
