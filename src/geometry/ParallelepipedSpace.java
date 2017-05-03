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
            for (Position deltaPosition : dimension.getDirections()) {
                if (contains(parallelepiped.getCorner()
                        .add(deltaPosition.scale(parallelepiped.getSize(dimension)/2 + 1)))) {
                    Parallelepiped neighbour = get(parallelepiped.getCorner()
                            .add(deltaPosition.scale(parallelepiped.getSize(dimension)/2 + 1)));
                    if (neighbour.getSize(dimension.getComplements()[0])
                            == parallelepiped.getSize(dimension.getComplements()[0])
                            && neighbour.getSize(dimension.getComplements()[1])
                            == parallelepiped.getSize(dimension.getComplements()[1])) {
                        parallelepipeds.remove(neighbour);

                        int newCenterComponent = (neighbour.getCorner().get(dimension)
                                * neighbour.getSize(dimension)
                                + (neighbour.getSize(dimension) + 1) % 2
                                + parallelepiped.getCorner().get(dimension) * parallelepiped.getSize(dimension))
                                / (neighbour.getSize(dimension) + parallelepiped.getSize(dimension));
                        Position newCenter = neighbour.getCorner().set(dimension, newCenterComponent);

                        newParallelepiped = neighbour
                                .setCorner(newCenter).setSize(dimension,
                                        neighbour.getSize(dimension) + parallelepiped.getSize(dimension));
                        break;
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
