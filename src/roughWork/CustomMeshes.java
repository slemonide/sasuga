package roughWork;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import jmeExamples.TestCustomMesh;

/**
 * Playing around with custom meshes
 */
public class CustomMeshes extends SimpleApplication {

    public static void main(String[] args){
        CustomMeshes app = new CustomMeshes();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(10);

        Mesh m = new Mesh();

        // Vertex positions in space
        Vector3f[] vertices = new Vector3f[3];
        vertices[0] = new Vector3f(0,0,0);
        vertices[1] = new Vector3f(0,1,0);
        vertices[2] = new Vector3f(0,1,1);

        // Texture coordinates
        Vector2f[] texCoord = new Vector2f[3];
        texCoord[0] = new Vector2f(0,0);
        texCoord[1] = new Vector2f(0,1);
        texCoord[2] = new Vector2f(1,0);

        // Indexes. We define the order in which mesh should be constructed
        short[] indexes = {0, 1, 2, 0};

        // Setting buffers
        m.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        m.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        m.setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        m.updateBound();

        // *************************************************************************
        // First mesh uses one solid color
        // *************************************************************************

        // Creating a geometry, and apply a single color material to it
        Geometry geom = new Geometry("OurMesh", m);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        // Attaching our geometry to the root node.
        rootNode.attachChild(geom);

        // *************************************************************************
        // Second mesh uses vertex colors to color each vertex
        // *************************************************************************
        Mesh cMesh = m.clone();
        Geometry coloredMesh = new Geometry("ColoredMesh", cMesh);
        Material matVC = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matVC.setBoolean("VertexColor", true);

        //We have 4 vertices and 4 color values for each of them.
        //If you have more vertices, you need 'new float[yourVertexCount * 4]' here!
        float[] colorArray = new float[4*4];
        int colorIndex = 0;

        //Set custom RGBA value for each Vertex. Values range from 0.0f to 1.0f
        for(int i = 0; i < 4; i++){
            // Red value (is increased by .2 on each next vertex here)
            colorArray[colorIndex++]= 0.1f+(.2f*i);
            // Green value (is reduced by .2 on each next vertex)
            colorArray[colorIndex++]= 0.9f-(0.2f*i);
            // Blue value (remains the same in our case)
            colorArray[colorIndex++]= 0.5f;
            // Alpha value (no transparency set here)
            colorArray[colorIndex++]= 1.0f;
        }
        // Set the color buffer
        cMesh.setBuffer(VertexBuffer.Type.Color, 4, colorArray);
        coloredMesh.setMaterial(matVC);
        // move mesh a bit so that it doesn't intersect with the first one
        coloredMesh.setLocalTranslation(4, 0, 0);
        rootNode.attachChild(coloredMesh);

//        /** Alternatively, you can show the mesh vertixes as points
//          * instead of coloring the faces. */
//        cMesh.setMode(Mesh.Mode.Points);
//        cMesh.setPointSize(10f);
//        cMesh.updateBound();
//        cMesh.setStatic();
//        Geometry points = new Geometry("Points", m);
//        points.setMaterial(mat);
//        rootNode.attachChild(points);

        // *************************************************************************
        // Third mesh will use a wireframe shader to show wireframe
        // *************************************************************************
        Mesh wfMesh = m.clone();
        Geometry wfGeom = new Geometry("wireframeGeometry", wfMesh);
        Material matWireframe = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matWireframe.setColor("Color", ColorRGBA.Green);
        matWireframe.getAdditionalRenderState().setWireframe(true);
        wfGeom.setMaterial(matWireframe);
        wfGeom.setLocalTranslation(4, 4, 0);
        rootNode.attachChild(wfGeom);

    }
}
