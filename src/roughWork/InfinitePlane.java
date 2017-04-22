package roughWork;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;

public class InfinitePlane extends SimpleApplication {
    public static void main(String[] args) {
        InfinitePlane app = new InfinitePlane();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Quad b = new Quad(100, 100);
        Spatial node = new Geometry("Box", b);

        Quaternion q = new Quaternion();
        node.setLocalRotation(q.fromAngleAxis(-FastMath.PI / 2, new Vector3f(1, 0, 0)));
        node.setLocalTranslation(-50, -1, 50);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        node.setMaterial(mat);

        rootNode.attachChild(node);
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}

