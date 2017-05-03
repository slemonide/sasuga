package geometry;

import model.Position;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a set of Parallelepiped instances
 * For each parallelepiped, there is a spatial in the assigned node
 */
public class ParallelepipedSpace {
    private Set<Parallelepiped> parallelepipeds;

    /**
     * Create an empty parallelepiped space associated with the given Node
     */
    public ParallelepipedSpace() {
        parallelepipeds = new HashSet<>();
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
            if (toSplit.getVolume() != 1) {
                // stub
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
}
