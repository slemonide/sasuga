package server.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import jme3tools.optimize.GeometryBatchFactory;
import server.model.Cell;
import server.model.WorldManager;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class VisualGUI extends SimpleApplication implements Observer {
    private static final double MIN_DELAY = 0.01;
    private Node cellsNode;
    private double delay;
    private boolean tick = false;

    private static VisualGUI instance;

    private VisualGUI() {};

    /**
     * Singleton pattern
     * @return instance
     */
    public static VisualGUI getInstance() {
        if(instance == null){
            instance = new VisualGUI();
        }
        return instance;
    }

    public static void main(String[] args) {
        VisualGUI app = new VisualGUI();

        AppSettings settings = new AppSettings(true);
        settings.setStereo3D(false);
        app.setSettings(settings);

        app.start();
    }

    @Override
    public void simpleInitApp() {
        delay = 0;

        flyCam.setMoveSpeed(10);

        getRootNode().attachChild(SkyFactory.createSky(getAssetManager(),
                "assets/Textures/Skysphere.jpg", SkyFactory.EnvMapType.SphereMap));
        addCells();
        addFloor();
        addShadows();
    }

    private void addCells() {
        cellsNode = new Node();
        cellsNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);
        rootNode.attachChild(cellsNode);

        Set<Cell> cells = WorldManager.getInstance().getRule().getCells();
        for (Cell cell : cells) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);
            node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            node.setLocalTranslation(cell.getPosition().x * 0.2f, cell.getPosition().y * 0.2f, cell.getPosition().z * 0.2f);

            cellsNode.attachChild(node);
        }

        GeometryBatchFactory.optimize(cellsNode);
    }

    private void addShadows() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
        rootNode.addLight(sun);

        /* Drop shadows */
        final int SHADOWMAP_SIZE=1024;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);

        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, 3);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.addFilter(dlsf);
        viewPort.addProcessor(fpp);
    }

    private void addFloor() {
        Geometry floor = new Geometry("Box", new Quad(2000, 2000));
        Material unshaded = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded.setColor("Color", ColorRGBA.White);
        floor.setMaterial(unshaded);
        floor.setShadowMode(RenderQueue.ShadowMode.Receive);

        Quaternion q = new Quaternion();
        floor.setLocalRotation(q.fromAngleAxis(-FastMath.PI / 2, new Vector3f(1, 0, 0)));
        floor.setLocalTranslation(-1000, -0.1f, 1000);
        rootNode.attachChild(floor);
    }

    @Override
    public void simpleUpdate(float tpf) {
        delay += tpf;
        if (delay <= MIN_DELAY) {
            return;
        }
        delay = 0;

        cellsNode.getChildren().clear();
        addCells();
        /*
        for (Cell cellToAdd : WorldManager.getInstance().getRule().getToAdd()) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);
            node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            node.setLocalTranslation(
                    cellToAdd.getPosition().x * 0.2f,
                    cellToAdd.getPosition().y * 0.2f,
                    cellToAdd.getPosition().z * 0.2f);

            cellsNode.attachChild(node);
        }*/

        for (Cell cellToRemove : WorldManager.getInstance().getRule().getToRemove()) {
            // stub
            // TODO: finish
        }

        GeometryBatchFactory.optimize(cellsNode);
        WorldManager.getInstance().getRule().tick();
    }

    private Set<Cell> filterVisible(Set<Cell> cells) {
        Set<Cell> visibleCells = new HashSet<>();
        return null;
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.equals("tick")) {
            tick = true;
        }
    }
}
