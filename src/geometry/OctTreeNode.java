package geometry;

import java.util.Optional;

/**
 * A node in an octree that knows it's parents and has 3d coordinates in its group
 *
 * A node group is a set of all nodes of the same geometrical size
 */
public class OctTreeNode {

    /**
     * Constructs an empty octTreeNode with coordinates (0,0,0)
     */
    public OctTreeNode() {

    }

    /**
     * Constructs an empty octTreeNode with the given coordinates
     *
     * @param coordinates coordinates of this node in the local node group
     */
    public OctTreeNode(Vector coordinates) {

    }

    /**
     * Add the given node to this tree at the coordinates provided
     *
     * <p>
     *     If given coordinates are already occupied,
     *     replace the node previously stored there
     * </p>
     * @param coordinates coordinates of the node related to this node
     * @param node node to be added
     */
    public void add(Vector coordinates, OctTreeNode node) {

    }

    /**
     * Remove the node at the coordinates provided
     *
     * <p>
     *     If no node is stored at the given coordinates,
     *     do nothing
     * </p>
     * @param coordinates coordinates of the node to remove
     */
    public void remove(Vector coordinates) {

    }

    /**
     * Maybe produce the node occupying the coordinates provided
     * @param coordinates coordinates at which to check for the node
     * @return possibly the node contained at the coordinates provided
     */
    public Optional<OctTreeNode> get(Vector coordinates) {
        // TODO: finish
        return Optional.empty();
    }

    /**
     * Produce the total number of nodes in this tree
     * @return the total number of nodes in this tree
     */
    public int size() {
        // TODO: finish
        return 0;
    }

    /**
     * Produce the total number of leaves in this tree
     * @return the total number of leaves in this tree
     */
    public int leafSize() {
        // TODO: finish
        return 0;
    }

    /**
     * Produce true if this node contains no children or parents,
     * false otherwise
     * @return true if this node contains no children or parents,
     * false otherwise
     */
    public boolean isEmpty() {
        // TODO: finish
        return false;
    }
}
