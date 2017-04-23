package ui;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
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
import com.jme3.util.SkyFactory;
import jme3tools.optimize.GeometryBatchFactory;
import model.Cell;
import model.Position;
import model.World;

import java.util.Collection;

public class VisualGUI extends SimpleApplication {
    private static final float SCALE = 0.2f;
    private static final double MIN_DELAY = 0.01;
    private Node cellsNode;
    private double delay;

    private boolean isPaused = false;
    private Geometry floor;
    private Position cursorPosition;

    public static void main(String[] args) {
        VisualGUI app = new VisualGUI();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        delay = 0;
        flyCam.setMoveSpeed(10);

        cursorPosition = new Position();
        initializeEnvironment();
        initializeEventHandlers();
    }

    private void initializeEnvironment() {
        addSkySphere();
        addCells();
        addFloor();
        addShadows();
    }

    private void addSkySphere() {
        getRootNode().attachChild(SkyFactory.createSky(getAssetManager(),
                "assets/Textures/Skysphere.jpg", SkyFactory.EnvMapType.SphereMap));
    }

    private void initializeEventHandlers() {
        inputManager.addMapping("PAUSE", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(pauseActionListener, "PAUSE");

        inputManager.addMapping("ADD_CELL", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addListener(addCellActionListener, "ADD_CELL");

        inputManager.addMapping("REMOVE_CELL", new KeyTrigger(KeyInput.KEY_L));
        inputManager.addListener(removeCellActionListener, "REMOVE_CELL");

        inputManager.addMapping("UP", new KeyTrigger(KeyInput.KEY_Y));
        inputManager.addListener(moveCursorUPActionListener, "UP");

        inputManager.addMapping("DOWN", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addListener(moveCursorDOWNActionListener, "DOWN");

        inputManager.addMapping("PX", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(moveCursorPXActionListener, "PX");

        inputManager.addMapping("NX", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addListener(moveCursorNXActionListener, "NX");

        inputManager.addMapping("PZ", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addListener(moveCursorPZActionListener, "PZ");

        inputManager.addMapping("NZ", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addListener(moveCursorNZActionListener, "NZ");
    }

    private ActionListener pauseActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            isPaused = !isPaused;
        }
    };

    private ActionListener addCellActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Position currentSelection = getCurrentSelection();
            World.getInstance().add(new Cell(currentSelection));
        }
    };

    private ActionListener removeCellActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            Position currentSelection = getCurrentSelection();
            World.getInstance().remove(currentSelection);
        }
    };

    private ActionListener moveCursorUPActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 1, 0);
        }
    };

    private ActionListener moveCursorDOWNActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, -1, 0);
        }
    };

    private ActionListener moveCursorPXActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(1, 0, 0);
        }
    };

    private ActionListener moveCursorNXActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(-1, 0, 0);
        }
    };

    private ActionListener moveCursorPZActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 0, 1);
        }
    };

    private ActionListener moveCursorNZActionListener = (name, pressed, tpf) -> {
        if (pressed) {
            cursorPosition = getCurrentSelection().add(0, 0, -1);
        }
    };

    private void addCells() {
        cellsNode = new Node();
        cellsNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);
        rootNode.attachChild(cellsNode);

        Collection<Cell> cells = World.getInstance().getCells();
        for (Cell cell : cells) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);
            node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
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
        floor = new Geometry("Box", new Quad(2000, 2000));
        Material unshaded = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded.setColor("Color", ColorRGBA.White);
        floor.setMaterial(unshaded);
        floor.setShadowMode(RenderQueue.ShadowMode.Receive);

        Quaternion q = new Quaternion();
        floor.setLocalRotation(q.fromAngleAxis(-FastMath.PI / 2, new Vector3f(1, 0, 0)));
        floor.setLocalTranslation(-1000, -SCALE/2, 1000);
        rootNode.attachChild(floor);

        updateFloor();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (!isPaused) {
            updateDelay(tpf);
            updateCells();
            updateFloor();
        }
    }

    private void updateFloor() {
        float minimumY = 0; // should be at least at the sea level

        for (Cell cell : World.getInstance().getCells()) {
            minimumY = Math.min(minimumY, cell.getPosition().getComponent(1) * SCALE);
        }

        Vector3f floorTranslation = floor.getLocalTranslation();
        Vector3f nextFloorTranslation = floorTranslation.setY(minimumY - SCALE/2);
        floor.setLocalTranslation(nextFloorTranslation);
    }

    private void updateDelay(float tpf) {
        delay = (delay + tpf) % MIN_DELAY;
    }

    private void updateCells() {
        cellsNode.getChildren().clear();
        addCells();
        GeometryBatchFactory.optimize(cellsNode);
        World.getInstance().tick();
    }

    /**
     * Stop the world thread when the application closes
     */
    @Override
    public void destroy() {
        World.getInstance().interrupt();
    }

    /**
     * Produces currently selected cell position
     * @return position
     */
    private Position getCurrentSelection() {
        return cursorPosition;
    }
}

