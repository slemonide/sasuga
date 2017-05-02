package geometry;

import com.jme3.scene.Node;
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

    /**
     * Create an empty parallelepiped space associated with the given Node
     */
    public ParallelepipedSpace() {
        parallelepipeds = new HashSet<>();
    }

    public void add(Position position) {
        remove(position);

        boolean done = false;
        for (Dimension dimension : Dimension.values()) {
            for (Position deltaPosition : dimension.getDirections()) {
                if (contains(position.add(deltaPosition))) {
                    Parallelepiped neighbour = get(position.add(deltaPosition));
                    if (neighbour.getSize(dimension.getComplements()[0]) == 1
                            && neighbour.getSize(dimension.getComplements()[1]) == 1) {
                        parallelepipeds.remove(neighbour);

                        int newCenterX = (neighbour.getCenter().get(dimension)
                                * neighbour.getSize(dimension)
                                + position.get(dimension)) / (neighbour.getSize(dimension) + 1);
                        Position newCenter = neighbour.getCenter().set(dimension, newCenterX);

                        Parallelepiped newParallelepiped = neighbour
                                .setCenter(newCenter).setSize(dimension, neighbour.getSize(dimension) + 1);

                        parallelepipeds.add(newParallelepiped);

                        done = true;
                        break;
                    }
                }
            }
        }

        if (!done) {
            parallelepipeds.add(new Parallelepiped(position));
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
