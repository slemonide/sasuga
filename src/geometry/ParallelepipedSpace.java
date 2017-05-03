package geometry;

import model.Position;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static geometry.Dimension.X;
import static geometry.Dimension.Y;
import static geometry.Dimension.Z;

/**
 * Represents a set of Parallelepiped instances
 * For each parallelepiped, there is a spatial in the assigned node
 */
public class ParallelepipedSpace {
    private Set<Parallelepiped> parallelepipeds;
    private Set<Parallelepiped> toAdd;
    private Set<Parallelepiped> toRemove;

    /**
     * Create an empty parallelepiped space associated with the given Node
     */
    public ParallelepipedSpace() {
        parallelepipeds = new HashSet<>();
        toAdd = new HashSet<>();
        toRemove = new HashSet<>();
    }

    public void add(Position position) {
        add(new Parallelepiped(position));
    }

    private void add(@NotNull Parallelepiped parallelepiped) {
        Parallelepiped newParallelepiped = null;
        for (Dimension dimension : Dimension.values()) {

            Position unitVector = dimension.getUnitVector();
            if (contains(parallelepiped.getCorner()
                    .add(unitVector.scale(parallelepiped.getSize(dimension))))) {
                Parallelepiped neighbour = get(parallelepiped.getCorner()
                        .add(unitVector.scale(parallelepiped.getSize(dimension))));
                assert neighbour != null;
                if (neighbour.getSize(dimension.getComplements()[0])
                        == parallelepiped.getSize(dimension.getComplements()[0])
                        && neighbour.getSize(dimension.getComplements()[1])
                        == parallelepiped.getSize(dimension.getComplements()[1])) {
                    parallelepipeds.remove(neighbour);
                    toRemove.add(neighbour);

                    Position newCorner = parallelepiped.getCorner();

                    newParallelepiped = neighbour
                            .setCorner(newCorner).setSize(dimension,
                                    neighbour.getSize(dimension) + parallelepiped.getSize(dimension));
                }
            }

            if (newParallelepiped == null) {
                if (contains(parallelepiped.getCorner()
                        .add(unitVector.inverse()))) {
                    Parallelepiped neighbour = get(parallelepiped.getCorner()
                            .add(unitVector.inverse()));
                    assert neighbour != null;
                    if (neighbour.getSize(dimension.getComplements()[0])
                            == parallelepiped.getSize(dimension.getComplements()[0])
                            && neighbour.getSize(dimension.getComplements()[1])
                            == parallelepiped.getSize(dimension.getComplements()[1])) {
                        parallelepipeds.remove(neighbour);
                        toRemove.add(neighbour);

                        Position newCorner = neighbour.getCorner();

                        newParallelepiped = neighbour
                                .setCorner(newCorner).setSize(dimension,
                                        neighbour.getSize(dimension) + parallelepiped.getSize(dimension));
                    }
                }
            }

            if (newParallelepiped != null) {
                break;
            }
        }

        if (newParallelepiped == null) {
            parallelepipeds.add(parallelepiped);
            toAdd.add(parallelepiped);
        } else {
            add(newParallelepiped);
        }
    }

    private boolean contains(@NotNull Position position) {
        return (get(position) != null);
    }

    public void remove(Position position) {
        if (contains(position)) {
            Parallelepiped toSplit = get(position);
            assert toSplit != null;

            parallelepipeds.remove(toSplit);
            toRemove.add(toSplit);
            if (toSplit.getVolume() != 1) {
                Set<Parallelepiped> toAdd = new HashSet<>();

                if (position.z > toSplit.getCorner().z) {
                    toAdd.add(toSplit.setSize(Z, position.z - toSplit.getCorner().z));
                }

                if (position.z < toSplit.getCorner().z + toSplit.getSize(Z) - 1) {
                    toAdd.add(toSplit.setSize(Z, toSplit.getCorner().z + toSplit.getSize(Z) - 1 - position.z)
                    .setCorner(toSplit.getCorner().set(Z, position.z + 1)));
                }

                if (position.y > toSplit.getCorner().y) {
                    toAdd.add(toSplit.setSize(Z, 1)
                            .setSize(Y, position.y - toSplit.getCorner().y)
                    .setCorner(toSplit.getCorner().set(Z, position.z)));
                }

                if (position.y < toSplit.getCorner().y + toSplit.getSize(Y) - 1) {
                    toAdd.add(toSplit.setSize(Z, 1)
                    .setSize(Y, toSplit.getCorner().y + toSplit.getSize(Y) - 1 - position.y)
                    .setCorner(toSplit.getCorner().set(Z, position.z).set(Y, position.y + 1)));
                }

                if (position.x > toSplit.getCorner().x) {
                    toAdd.add(toSplit.setSize(Z, 1).setSize(Y, 1)
                    .setSize(X, position.x - toSplit.getCorner().x)
                    .setCorner(toSplit.getCorner().set(Z, position.z)
                    .set(Y, position.y)));
                }

                if (position.x < toSplit.getCorner().x + toSplit.getSize(X) - 1) {
                    toAdd.add(toSplit.setSize(Z, 1).setSize(Y, 1)
                    .setSize(X, toSplit.getCorner().x + toSplit.getSize(X) - 1 - position.x)
                            .setCorner(toSplit.getCorner().set(Z, position.z)
                                    .set(Y, position.y).set(X, position.x + 1)));
                }

                for (Parallelepiped parallelepiped : toAdd) {
                    add(parallelepiped);
                }
            }
        }
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
        return parallelepipeds;
    }

    public int size() {
        return parallelepipeds.size();
    }

    public boolean isEmpty() {
        return parallelepipeds.isEmpty();
    }

    /**
     * @return set of parallelepipeds added since last modification
     */
    public Set<Parallelepiped> getToAdd() {
        Set<Parallelepiped> toReturn = toAdd;
        toAdd = new HashSet<>();

        return toReturn;
    }

    /**
     * @return set of parallelepipeds removed since last modification
     */
    public Set<Parallelepiped> getToRemove() {
        Set<Parallelepiped> toReturn = toRemove;
        toRemove = new HashSet<>();

        return toReturn;
    }
}
