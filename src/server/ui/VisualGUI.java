package server.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import server.model.Cell;
import server.model.WorldManager;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class VisualGUI extends SimpleApplication implements Observer {
    private static final double MIN_DELAY = 0.5;
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
    }

    @Override
    public void simpleUpdate(float tpf) {
        delay += tpf;
        if (delay <= MIN_DELAY) {
            return;
        }
        delay = 0;
        rootNode.getChildren().clear();
        Set<Cell> cells = WorldManager.getInstance().getCells();
        for (Cell cell : cells) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            node.setLocalTranslation(cell.getPosition().x * 0.2f, cell.getPosition().y * 0.2f, cell.getPosition().z * 0.2f);

            rootNode.attachChild(node);
        }
        WorldManager.getInstance().tick();

        //Set<Cell> visibleCells = filterVisible(WorldManager.getInstance().getCells());
        /*
        for (int i = 0; i < WorldManager.getInstance().getCells().size(); i++) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            float x = (float) (50 * Math.random() - 25);
            float y = (float) (50 * Math.random() - 25);
            float z = (float) (50 * Math.random() - 25);

            node.setLocalTranslation(x, y, z);

            rootNode.attachChild(node);
        }*/
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
