package geometry;

import java.util.Optional;

/**
 * A node in a graph. Uses vectors as keys to access it's children
 */
public class Node {

    /**
     * Construct an empty node with no children
     */
    public Node() {

    }

    /**
     * Add a sub-node to this node at the given relative coordinates
     *
     * <p>
     *     If there is a node at the given coordinates, return it
     *     and delete it from this node
     * </p>
     * @param relation relative coordinates
     * @param child sub-node to be add
     * @return maybe a node if there was a node at the given relation
     */
    public Optional<Node> add(Vector relation, Node child) {
        return Optional.empty();
    }


}
