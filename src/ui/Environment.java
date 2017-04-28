package ui;

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
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.util.SkyFactory;
import jme3tools.optimize.GeometryBatchFactory;
import model.Cell;
import model.MaterialManager;
import model.Position;
import model.World;

import java.util.*;
import java.util.stream.Collectors;

class Environment implements Observer {
    public static final float SCALE = 0.2f;
    private static final Mesh BOX = new Box(SCALE/2, SCALE/2, SCALE/2);;
    private static final float FLOOR_SIZE = 5000;
    private final VisualGUI visualGUI;
    private Node cellsNode;
    private Map<Position, Spatial> voxelMap;
    private Set<Cell> toAdd;
    private Set<Position> toRemove;
    private boolean updatingCells;

    private Node getCellsNode() {
        return cellsNode;
    }

    private Geometry floor;

    private Geometry getFloor() {
        return floor;
    }

    Environment(VisualGUI visualGUI) {
        this.visualGUI = visualGUI;

        voxelMap = new HashMap<>();
        toAdd = new HashSet<>();
        toRemove = new HashSet<>();
        updatingCells = false;

        World.getInstance().addObserver(this);
    }

    void initializeEnvironment() {
        addSkySphere();
        addCells();
        addFloor();
        addShadows();
    }

    private void addSkySphere() {
        visualGUI.getRootNode().attachChild(SkyFactory.createSky(visualGUI.getAssetManager(),
                "assets/Textures/Skysphere.jpg", SkyFactory.EnvMapType.SphereMap));
    }

    private void addCells() {
        cellsNode = new Node();
        cellsNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        visualGUI.getRootNode().setShadowMode(RenderQueue.ShadowMode.Off);
        visualGUI.getRootNode().attachChild(cellsNode);

        updateCells();
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
        floor.setShadowMode(RenderQueue.ShadowMode.Receive);

        Quaternion q = new Quaternion();
        floor.setLocalRotation(q.fromAngleAxis(-FastMath.PI / 2, new Vector3f(1, 0, 0)));
        floor.setLocalTranslation(-FLOOR_SIZE / 2, -SCALE / 2, FLOOR_SIZE / 2);
        visualGUI.getRootNode().attachChild(floor);

        updateFloor();
    }

    void update(float tpf) {
        updateCells();
        updateFloor();
    }

    private void updateFloor() {
        float minimumY = 0; // should be at least at the sea level

        for (Cell cell : World.getInstance().getCells()) {
            minimumY = Math.min(minimumY, cell.getPosition().getComponent(1) * SCALE);
        }

        Vector3f floorTranslation = getFloor().getLocalTranslation();
        Vector3f nextFloorTranslation = floorTranslation.setY(minimumY - SCALE/2);
        getFloor().setLocalTranslation(nextFloorTranslation);
    }

    private void updateCells() {
        updatingCells = true;
        for (Cell cell : toAdd) {
            Position position = cell.getPosition();

            removeSpatial(position);
            addSpatial(cell, position);
        }

        for (Position position : toRemove) {
            removeSpatial(position);
        }

        toAdd.clear();
        toRemove.clear();
        updatingCells = false;
    }

    private void addSpatial(Cell cell, Position position) {
        Spatial node = new Geometry("Box", BOX);
        node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Material material = MaterialManager.getInstance().getColoredMaterial(visualGUI.getAssetManager(), cell.getColor());
        node.setMaterial(material);

        node.setLocalTranslation(
                position.getComponent(0) * SCALE,
                position.getComponent(1) * SCALE,
                position.getComponent(2) * SCALE);

        cellsNode.attachChild(node);
        voxelMap.put(position, node);
    }

    private void removeSpatial(Position position) {
        Spatial spatialToRemove = voxelMap.get(position);

        if (spatialToRemove != null) {
            cellsNode.detachChild(spatialToRemove);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // this could cause problems
        boolean done = false;
        while (!done) {
            if (!updatingCells) {
                toAdd.addAll(World.getInstance().getToAdd());
                toRemove.addAll(World.getInstance().getToRemove());
                done = true;
            }
        }
    }
}