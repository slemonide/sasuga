package ui;

import com.jme3.scene.Node;
import world.CellStorage;

/**
 * Generates a graphical representation of a CellStorage
 */
public class CellStorageRenderer {

    /**
     * Creates a renderer with a given assigned cellStorage and
     * calls render() on it
     *
     * @param cellStorage CellStorage to which to assign this renderer
     */
    public CellStorageRenderer(CellStorage cellStorage) {

    }

    /**
     * Produce node representing the current rendering of the cellStorage
     *
     * <p>
     *     Returned Node will be updated automatically each time render()
     *     method is called.
     *
     *     Calling getNode
     * </p>
     *
     * @return node representing the current rendering of the cellStorage
     */
    public Node getNode() {
        // TODO: finish
        return new Node();
    }

    /**
     * Update the current rendering of the cellStorage
     */
    public void render() {
        // TODO: finish
    }

    /**
     * Produce the total number of parallelepipeds rendered on the scene
     * @return the total number of parallelepipeds rendered on the scene
     */
    public int size() {
        // TODO: finish
        return 0;
    }

    /**
     * Produce the total volume of parallelepipeds rendered on the scene
     * @return the total volume of parallelepipeds rendered on the scene
     */
    public long volume() {
        // TODO: finish
        return 0;
    }
}
