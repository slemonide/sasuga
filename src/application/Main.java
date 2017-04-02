package application;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import static java.lang.Math.random;

public class Main extends SimpleApplication {

    private static final int MAX_NODES = 100 * 100 * 2;
    private static final double MIN_DELAY = 0.0001;
    private double delay;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        delay = 0;

        int i;
        for (i = 0; i < MAX_NODES; i++) {
            Box b = new Box(0.1f, 0.1f, 0.1f);
            Spatial node = new Geometry("Box", b);

            Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
            node.setMaterial(mat);

            float x = (float) (100 * random() - 50);
            float y = (float) (100 * random() - 50);
            float z = (float) (100 * random() - 50);

            node.setLocalTranslation(x, y, z);

            node.setLocalRotation(new Quaternion((float) (Math.random() * 2 * Math.PI),
                    (float) (Math.random() * 2 * Math.PI),
                    (float) (Math.random() * 2 * Math.PI),
                    (float) (Math.random() * 2 * Math.PI)));

            node.setLocalScale((float) (Math.random() + 0.5),
                    (float) (Math.random() + 0.5), (float) (Math.random() + 0.5));

            rootNode.attachChild(node);
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        delay += tpf;
        if (delay <= MIN_DELAY) {
            return;
        }
        delay = 0;

        for (Spatial node : rootNode.getChildren()) {
            node.setLocalRotation(new Quaternion(
                    (float) ((Math.random() - 0.5) * Math.random() * 0.1 + node.getLocalRotation().getX()),
                    (float) ((Math.random() - 0.5) * Math.random() * 0.1 + node.getLocalRotation().getY()),
                    (float) ((Math.random() - 0.5) * Math.random() * 0.1 + node.getLocalRotation().getZ()),
                    (float) ((Math.random() - 0.5) * Math.random() * 0.1 + node.getLocalRotation().getW())));
/*
            node.setLocalScale(
                    (float) (2 * Math.sin((Math.random() - 0.5) * Math.random() + node.getLocalScale().x)),
                    (float) (2 * Math.sin((Math.random() - 0.5) * Math.random() + node.getLocalScale().y)),
                    (float) (2 * Math.sin((Math.random() - 0.5) * Math.random() + node.getLocalScale().z)));
                    */
        }
/*
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
        */
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
