package geometry;

import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import model.Cell;
import model.MaterialManager;
import model.Position;
import ui.Coordinates;
import ui.VisualGUI;

import java.util.HashSet;
import java.util.Set;

import static ui.Environment.SCALE;

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

    public void add(Cell cell) {
        Spatial voxel = new Geometry("Box", new Box(SCALE/2,SCALE/2,SCALE/2));
        voxel.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Material material = new Material(VisualGUI.getInstance().getAssetManager(),
                "Common/MatDefs/Misc/ShowNormals.j3md");

        voxel.setLocalTranslation(Coordinates.positionToVector(cell.getPosition()));

        node.attachChild(voxel);
        parallelepipeds.add(new Parallelepiped(cell.getPosition()));
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
