package ui;

import util.CollectionObserver;
import world.CellParallelepiped;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.util.SkyFactory;
import config.Options;
import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import world.World;
import util.Difference;
import util.SetObserver;

import java.util.*;

import static geometry.Axis.X;
import static geometry.Axis.Y;
import static geometry.Axis.Z;

public class Environment {
    /**
     * World scale
     */
    private static final float SCALE = Options.getInstance().getFloat("SCALE");
    /**
     * Side size of the floor spatial
     */
    private static final float FLOOR_SIZE = 5000;
    /**
     * The main gui app
     */
    private final VisualGUI visualGUI;
    /**
     * Node to which all spatials that represent cells are connected
     */
    private Node cellsNode;
    /**
     * Renderer that takes care of the cellsNode
     */
    private CellStorageRenderer renderer;

    private Geometry floor;

    private Geometry getFloor() {
        return floor;
    }

    Environment(VisualGUI visualGUI) {
        this.visualGUI = visualGUI;

        renderer = new CellStorageRenderer(World.getInstance().getCellStorage());
    }

    /**
     * Initializes the environment using the configurable options
     */
    void initializeEnvironment() {
        if (Options.getInstance().getBoolean("ENABLE_SKY_SPHERE")) {
            addSkySphere();
        }
        if (Options.getInstance().getBoolean("ENABLE_FLOOR")) {
            addFloor();
        }
        if (Options.getInstance().getBoolean("ENABLE_SHADOWS")) {
            addShadows();
        }

        addCells();
    }

    /**
     * Initializes the sky sphere
     */
    private void addSkySphere() {
        visualGUI.getRootNode().attachChild(SkyFactory.createSky(visualGUI.getAssetManager(),
                "Textures/Skysphere.jpg", SkyFactory.EnvMapType.SphereMap));
    }

    /**
     * Initialize the cell node and fill it with the already existing cell parallelepipeds
     * ALso, optionally configure the shadows
     */
    private void addCells() {
        cellsNode = renderer.getNode();
        visualGUI.getRootNode().attachChild(cellsNode);

        // Turn on shadows
        if (Options.getInstance().getBoolean("ENABLE_SHADOWS")) {
            cellsNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
            visualGUI.getRootNode().setShadowMode(RenderQueue.ShadowMode.Off);
        }
    }

    /**
     * Initialize global directional shadows
     */
    private void addShadows() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        visualGUI.getRootNode().addLight(sun);

        /* Drop shadows */
        final int SHADOWMAP_SIZE = 1024;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(visualGUI.getAssetManager(), SHADOWMAP_SIZE, 3);
        dlsr.setLight(sun);
        visualGUI.getViewPort().addProcessor(dlsr);

        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(visualGUI.getAssetManager(), SHADOWMAP_SIZE, 3);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(visualGUI.getAssetManager());
        fpp.addFilter(dlsf);
        visualGUI.getViewPort().addProcessor(fpp);
    }

    /**
     * Initialize the floor (looks nicely with the shadows)
     */
    private void addFloor() {
        floor = new Geometry("Box", new Quad(FLOOR_SIZE, FLOOR_SIZE));
        Material unshaded = new Material(visualGUI.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded.setColor("Color", ColorRGBA.White);
        floor.setMaterial(unshaded);

        if (Options.getInstance().getBoolean("ENABLE_SHADOWS")) {
            floor.setShadowMode(RenderQueue.ShadowMode.Receive);
        }

        Quaternion q = new Quaternion();
        floor.setLocalRotation(q.fromAngleAxis(-FastMath.PI / 2, new Vector3f(1, 0, 0)));
        floor.setLocalTranslation(-FLOOR_SIZE / 2, -SCALE / 2, FLOOR_SIZE / 2);
        visualGUI.getRootNode().attachChild(floor);

        updateFloor();
    }

    /**
     * Update the floor position and the visible cells
     * @param tpf time since the last invoke
     */
    void update(float tpf) {
        if (Options.getInstance().getBoolean("ENABLE_FLOOR")) {
            updateFloor();
        }

        renderer.render();
    }

    /**
     * Move the floor so that all of the visible cells are above it
     */
    private void updateFloor() {
        float minimumY = 0; // should be at least at the sea level

        for (CellParallelepiped cellParallelepiped : World.getInstance().getCells()) {
            minimumY = Math.min(minimumY, cellParallelepiped.parallelepiped.getCorner().y * SCALE);
        }

        Vector3f floorTranslation = getFloor().getLocalTranslation();
        Vector3f nextFloorTranslation = floorTranslation.setY(minimumY - SCALE/2);
        getFloor().setLocalTranslation(nextFloorTranslation);
    }

    private void add(Parallelepiped parallelepiped) {
        Mesh box;
        if (Options.getInstance().getBoolean("ENABLE_WIREFRAMES")) {
            box = new WireBox(
                    parallelepiped.getSize(X) * SCALE/2,
                    parallelepiped.getSize(Y) * SCALE/2,
                    parallelepiped.getSize(Z) * SCALE/2);
        } else {
            box = new Box(
                    parallelepiped.getSize(X) * SCALE/2,
                    parallelepiped.getSize(Y) * SCALE/2,
                    parallelepiped.getSize(Z) * SCALE/2);
        }

        Spatial node = new Geometry("Box", box);
        node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Material material = MaterialManager.getInstance()
                .getColoredMaterial(visualGUI.getAssetManager(), null);
        node.setMaterial(material);

        node.setLocalTranslation(parallelepiped.getWorldVector3f().mult(SCALE));

        cellsNode.attachChild(node);
    }

    int getNumberOfParallelepipeds() {
        return renderer.size();
    }

    long getVolumeOfParallelepipeds() {
        return renderer.volume();
    }
}