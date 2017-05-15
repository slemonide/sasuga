package ui;

import cells.CellParallelepiped;
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

public class Environment implements Observer {
    private static final float SCALE = Options.getInstance().getFloat("SCALE");
    private static final float FLOOR_SIZE = 5000;
    private final VisualGUI visualGUI;
    private Node cellsNode;
    private Map<Parallelepiped, Spatial> voxelMap;
    private ParallelepipedSpace parallelepipedSpace;
    private SetObserver<Parallelepiped> parallelepipedSpaceObserver;
    private Queue<CellParallelepiped> toAdd;
    private Queue<Position> toRemove;

    private Geometry floor;

    private Geometry getFloor() {
        return floor;
    }

    Environment(VisualGUI visualGUI) {
        this.visualGUI = visualGUI;

        voxelMap = new HashMap<>();
        parallelepipedSpace = new ParallelepipedSpace();
        parallelepipedSpaceObserver = new SetObserver<>(parallelepipedSpace.getParallelepipeds());
        toAdd = new LinkedList<>();
        toRemove = new LinkedList<>();

        World.getInstance().addObserver(this);
    }

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

    private void addSkySphere() {
        visualGUI.getRootNode().attachChild(SkyFactory.createSky(visualGUI.getAssetManager(),
                "Textures/Skysphere.jpg", SkyFactory.EnvMapType.SphereMap));
    }

    private void addCells() {
        cellsNode = new Node();
        visualGUI.getRootNode().attachChild(cellsNode);

        if (Options.getInstance().getBoolean("ENABLE_SHADOWS")) {
            cellsNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
            visualGUI.getRootNode().setShadowMode(RenderQueue.ShadowMode.Off);
        }

        toAdd.addAll(World.getInstance().getCells());
    }

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

    void update(float tpf) {
        if (Options.getInstance().getBoolean("ENABLE_FLOOR")) {
            updateFloor();
        }

        updateCells();
    }

    private void updateFloor() {
        float minimumY = 0; // should be at least at the sea level

        for (CellParallelepiped cellParallelepiped : World.getInstance().getCells()) {
            minimumY = Math.min(minimumY, cellParallelepiped.parallelepiped.getCorner().y * SCALE);
        }

        Vector3f floorTranslation = getFloor().getLocalTranslation();
        Vector3f nextFloorTranslation = floorTranslation.setY(minimumY - SCALE/2);
        getFloor().setLocalTranslation(nextFloorTranslation);
    }

    private void updateCells() {
        while (toAdd.peek() != null) {
            CellParallelepiped cellParallelepiped = toAdd.remove();

            addSpatial(cellParallelepiped);
        }

        while (toRemove.peek() != null) {
            Position position = toRemove.remove();

            removeSpatial(position);
        }
    }

    private void addSpatial(CellParallelepiped cellParallelepiped) {
        removeSpatial(cellParallelepiped.parallelepiped.getCorner());

        parallelepipedSpace.add(cellParallelepiped.parallelepiped.getCorner());

        updateSpatials();
    }

    private void updateSpatials() {
        Difference<Collection<Parallelepiped>> difference = parallelepipedSpaceObserver.getDifference();

        for (Parallelepiped parallelepiped : difference.getRemoved()) {
            remove(parallelepiped);
        }

        for (Parallelepiped parallelepiped : difference.getAdded()) {
            add(parallelepiped);
        }
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
        voxelMap.put(parallelepiped, node);
    }

    private void remove(Parallelepiped parallelepiped) {
        assert voxelMap.containsKey(parallelepiped);

        cellsNode.detachChild(voxelMap.get(parallelepiped));
        voxelMap.remove(parallelepiped);
    }

    private void removeSpatial(Position position) {
        parallelepipedSpace.remove(position);

        updateSpatials();
    }

    @SuppressWarnings("UseBulkOperation")
    @Override
    public void update(Observable o, Object arg) {
        // NOTE: addAll won't work here
        for (CellParallelepiped cellParallelepiped : World.getInstance().getToAdd()) {
            toAdd.add(cellParallelepiped);
        }
        for (Position position : World.getInstance().getToRemove()) {
            toRemove.add(position);
        }
    }

    int getNumberOfParallelepipeds() {
        return parallelepipedSpace.size();
    }

    long getVolumeOfParallelepipeds() {
        return parallelepipedSpace.getVolume();
    }
}