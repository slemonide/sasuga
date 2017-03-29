package application;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * This is the Server Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private static final int MAX_NODES = 50 * 50 * 2;
    private static final double MIN_DELAY = 10;
    private double delay;

    public static void main(String[] args) {
        Main app = new Main();
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

        rootNode.detachAllChildren();

        for (int i = 0; i < MAX_NODES; i++) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            float x = (float) (50 * Math.random() - 25);
            float y = (float) (50 * Math.random() - 25);
            float z = (float) (50 * Math.random() - 25);

            node.setLocalTranslation(x, y, z);

            rootNode.attachChild(node);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code

        rm.setCamera(new Camera(100, 100), true);
    }
}
