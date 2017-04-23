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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.util.SkyFactory;
import jme3tools.optimize.GeometryBatchFactory;
import model.Cell;
import model.World;

import java.util.Collection;

class Environment {
    private final VisualGUI visualGUI;
    private Node cellsNode;
    private static final float SCALE = 0.2f;
    private static final double MIN_DELAY = 0.01;
    private float delay;

    private Node getCellsNode() {
        return cellsNode;
    }

    private Geometry floor;

    private Geometry getFloor() {
        return floor;
    }

    Environment(VisualGUI visualGUI) {
        delay = 0;
        this.visualGUI = visualGUI;
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

        Collection<Cell> cells = World.getInstance().getOldCells();
        for (Cell cell : cells) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);
            node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

            Material mat = new Material(visualGUI.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            node.setLocalTranslation(
                    cell.getPosition().getComponent(0) * SCALE,
                    cell.getPosition().getComponent(1) * SCALE,
                    cell.getPosition().getComponent(2) * SCALE);

            cellsNode.attachChild(node);
        }

        GeometryBatchFactory.optimize(cellsNode);
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
        floor = new Geometry("Box", new Quad(2000, 2000));
        Material unshaded = new Material(visualGUI.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded.setColor("Color", ColorRGBA.White);
        floor.setMaterial(unshaded);
        floor.setShadowMode(RenderQueue.ShadowMode.Receive);

        Quaternion q = new Quaternion();
        floor.setLocalRotation(q.fromAngleAxis(-FastMath.PI / 2, new Vector3f(1, 0, 0)));
        floor.setLocalTranslation(-1000, -SCALE / 2, 1000);
        visualGUI.getRootNode().attachChild(floor);

        updateFloor();
    }

    void update(float tpf) {
        updateDelay(tpf);
        updateCells();
        updateFloor();
    }

    private void updateFloor() {
        float minimumY = 0; // should be at least at the sea level

        for (Cell cell : World.getInstance().getOldCells()) {
            minimumY = Math.min(minimumY, cell.getPosition().getComponent(1) * SCALE);
        }

        Vector3f floorTranslation = getFloor().getLocalTranslation();
        Vector3f nextFloorTranslation = floorTranslation.setY(minimumY - SCALE/2);
        getFloor().setLocalTranslation(nextFloorTranslation);
    }

    private void updateDelay(float tpf) {
        delay = (float) ((delay + tpf) % MIN_DELAY);
    }

    private void updateCells() {
        getCellsNode().getChildren().clear();
        addCells();
        GeometryBatchFactory.optimize(getCellsNode());
    }
}