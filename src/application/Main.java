package application;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private static final int MAX_NODES = 100 * 100 * 2;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        for (int i = 0; i < MAX_NODES; i++) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            float x = (float) (100 * Math.random());
            float y = (float) (100 * Math.random());
            float z = (float) (100 * Math.random());

            node.setLocalTranslation(x, y, z);

            rootNode.attachChild(node);
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
