package server.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.util.SkyFactory;
import server.model.Cell;
import server.model.WorldManager;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class VisualGUI extends SimpleApplication implements Observer {
    private static final double MIN_DELAY = 0.5;
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
        app.start();
    }

    @Override
    public void simpleInitApp() {
        delay = 0;

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
        rootNode.addLight(sun);

        cellsNode = new Node();
        cellsNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        rootNode.attachChild(cellsNode);
    }

    @Override
    public void simpleUpdate(float tpf) {
        delay += tpf;
        if (delay <= MIN_DELAY) {
            return;
        }
        delay = 0;
        cellsNode.getChildren().clear();
        Set<Cell> cells = WorldManager.getInstance().getRule().getCells();
        for (Cell cell : cells) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            node.setLocalTranslation(cell.getPosition().x * 0.2f, cell.getPosition().y * 0.2f, cell.getPosition().z * 0.2f);

            cellsNode.attachChild(node);
        }
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
