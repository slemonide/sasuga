package client.ui;

import client.model.ServerManager;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
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
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import jme3tools.optimize.GeometryBatchFactory;
import server.model.Cell;
import server.model.WorldManager;

import java.io.IOException;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>, jacketsj <jacketsj@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A 3D client
 */
public class VisualGUI extends SimpleApplication {
    private static final float SCALE = 0.2f;
    private static final double MIN_DELAY = 2;
    private Node cellsNode;
    private double delay;

    //which dimensions are rendered
    public static int xdim;
    public static int ydim;
    public static int zdim;

    private boolean isPaused = false;

    public static void main(String[] args) {
        VisualGUI app = new VisualGUI();

        AppSettings settings = new AppSettings(true);
        settings.setStereo3D(false);
        app.setSettings(settings);

        app.xdim = 0;
        app.ydim = 1;
        app.zdim = 2;

        try {
            ServerManager.getInstance().connect();
            app.start();
        } catch (IOException e) {
            System.out.println("ERROR: Cannot connect to the server");
            e.printStackTrace();
        }
    }

    @Override
    public void simpleInitApp() {
        delay = 0;
        flyCam.setMoveSpeed(10);

        addSkySphere();
        initializeCells();
        addFloor();
        addShadows();
        addEventHandlers();
    }

    private void initializeCells() {
        cellsNode = new Node();
        cellsNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);
        rootNode.attachChild(cellsNode);

        addCells();
    }

    private void addSkySphere() {
        getRootNode().attachChild(SkyFactory.createSky(getAssetManager(),
                "assets/Textures/Skysphere.jpg", SkyFactory.EnvMapType.SphereMap));
    }

    private void addEventHandlers() {
        inputManager.addMapping("PAUSE", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("PREVDIM", new KeyTrigger(KeyInput.KEY_MINUS));
        inputManager.addMapping("NEXTDIM", new KeyTrigger(KeyInput.KEY_EQUALS));
        inputManager.addListener(pauseActionListener, "PAUSE");
        inputManager.addListener(prevDimActionListener, "PREVDIM");
        inputManager.addListener(nextDimActionListener, "NEXTDIM");
    }

    private void addCells() {
        try {
            for (Cell cell : ServerManager.getInstance().getCells()) {
                Box b = new Box(0.1f, 0.1f, 0.1f);
                Spatial node = new Geometry("Box", b);
                node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

                Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
                node.setMaterial(mat);

                node.setLocalTranslation(cell.getPosition().v[xdim] * SCALE, cell.getPosition().v[ydim] * SCALE, cell.getPosition().v[zdim] * SCALE);

                cellsNode.attachChild(node);
            }

            GeometryBatchFactory.optimize(cellsNode);
        } catch (IOException e) {
            System.out.println("ERROR: did not get cells set");
            e.printStackTrace();
        }
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
        if (!isPaused) {
            updateCells(tpf);
        }
    }

    private ActionListener pauseActionListener = new ActionListener(){
        public void onAction(String name, boolean pressed, float tpf){
            if (pressed) {
                isPaused = !isPaused;
            }
        }
    };

    private ActionListener prevDimActionListener = new ActionListener(){
        public void onAction(String name, boolean pressed, float tpf){
            if (pressed) {
                int dim = WorldManager.getInstance().dim;
                xdim = (xdim - 1+dim) % dim;
                ydim = (ydim - 1+dim) % dim;
                zdim = (zdim - 1+dim) % dim;
            }
        }
    };

    private ActionListener nextDimActionListener = new ActionListener(){
        public void onAction(String name, boolean pressed, float tpf){
            if (pressed) {
                int dim = WorldManager.getInstance().dim;
                xdim = (xdim + 1) % dim;
                ydim = (ydim + 1) % dim;
                zdim = (zdim + 1) % dim;
            }
        }
    };

    private void updateCells(float tpf) {
        delay += tpf;
        if (delay <= MIN_DELAY) {
            return;
        }
        delay = 0;

        cellsNode.getChildren().clear();
        addCells();

        GeometryBatchFactory.optimize(cellsNode);
    }
}
