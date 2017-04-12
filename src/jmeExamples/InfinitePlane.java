package jmeExamples;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import server.model.Cell;

import java.util.HashSet;
import java.util.Set;

public class InfinitePlane extends SimpleApplication {
    private static final double MIN_DELAY = 2;
    private double delay;

    public static void main(String[] args) {
        InfinitePlane app = new InfinitePlane();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        delay = 0;

        Box b = new Box(10000, 0.1f, 1);
        Spatial node = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        node.setMaterial(mat);

        rootNode.attachChild(node);

        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
        rootNode.addLight(sun);
    }

    @Override
    public void simpleUpdate(float tpf) {

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
}

