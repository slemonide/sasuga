package geometry;

import com.jme3.scene.Node;
import model.Cell;
import model.Position;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a set of Parallelepiped instances
 * For each parallelepiped, there is a spatial in the assigned node
 */
public class ParallelepipedSpace {
    private Node node;
    private Set<Parallelepiped> parallelepipeds;

    /**
     * Create an empty parallelepiped space associated with the given Node
     */
    public ParallelepipedSpace(Node parallelepipedSpaceNode) {
        this.node = parallelepipedSpaceNode;
        parallelepipeds = new HashSet<>();
    }

    private void rebuildSpatials() {
        // TODO: implement
    }

    public void add(Cell cell) {
        Position position = cell.getPosition();

        remove(position);
        parallelepipeds.add(new Parallelepiped(position));

        rebuildSpatials();
    }

    public void remove(Position position) {
        Parallelepiped toRemove = get(position);
        if (toRemove != null) {
            parallelepipeds.remove(toRemove);
            node.detachChild(toRemove.getSpatial());

            rebuildSpatials();
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

    /*
    private void addSpatial(Cell cell) {
        removeSpatial(cell.getPosition());

        Spatial node = new Geometry("Box", BOX);
        node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Material material = MaterialManager.getInstance()
                .getColoredMaterial(visualGUI.getAssetManager(), cell.getColor());
        node.setMaterial(material);

        node.setLocalTranslation(Coordinates.positionToVector(cell.getPosition()));

        cellsNode.attachChild(node);
        voxelMap.put(cell.getPosition(), node);
    }

    private void removeSpatial(Position position) {
        if (voxelMap.containsKey(position)) {
            cellsNode.detachChild(voxelMap.get(position));
        }
    }
     */
}
