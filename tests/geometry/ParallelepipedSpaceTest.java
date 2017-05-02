package geometry;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import model.Cell;
import model.Position;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.Environment;

import java.util.Vector;

import static org.junit.Assert.*;
import static ui.Environment.SCALE;

public class ParallelepipedSpaceTest {
    private Node testNode;
    private ParallelepipedSpace testSpace;

    @Before
    public void runBefore() {
        testNode = new Node();
        testSpace = new ParallelepipedSpace(testNode);
    }

    @Test
    public void testConstructor() {
        assertTrue(testNode.getChildren().isEmpty());
    }

    @Test
    public void testSimpleAdd() {
        testSpace.add(new Cell(new Position()));
        assertEquals(1, testNode.getChildren().size());
        assertEquals(4, testNode.getChild(0).getVertexCount());
        assertEquals(new Geometry("Box", new Box(SCALE/2,SCALE/2,SCALE/2)),
                (Geometry) testNode.getChild(0));
        assertEquals(new Vector3f(0,0,0), testNode.getChild(0).getLocalTranslation());
    }

    @Test
    public void testAddTwice() {
        testSpace.add(new Cell(new Position()));
        testSpace.add(new Cell(new Position()));
        assertEquals(1, testNode.getChildren().size());
        assertEquals(new Vector3f(0,0,0), testNode.getChild(0).getLocalTranslation());
    }

    @Test
    public void testAddTwoX() {
        testSpace.add(new Cell(new Position(1,0,0)));
        testSpace.add(new Cell(new Position(0,0,0)));
        assertEquals(1, testNode.getChildren().size());
        assertEquals(new Geometry("Box", new Box(SCALE,SCALE/2,SCALE/2)),
                (Geometry) testNode.getChild(0));
        assertEquals(new Vector3f(0.5f,0,0), testNode.getChild(0).getLocalTranslation());
    }

    @Test
    public void testAddTwoY() {
        testSpace.add(new Cell(new Position(0,1,0)));
        testSpace.add(new Cell(new Position(0,0,0)));
        assertEquals(1, testNode.getChildren().size());
        assertEquals(new Geometry("Box", new Box(SCALE/2,SCALE,SCALE/2)),
                (Geometry) testNode.getChild(0));
        assertEquals(new Vector3f(0,0.5f,0), testNode.getChild(0).getLocalTranslation());
    }

    @Test
    public void testAddTwoZ() {
        testSpace.add(new Cell(new Position(0,0,1)));
        testSpace.add(new Cell(new Position(0,0,0)));
        assertEquals(1, testNode.getChildren().size());
        assertEquals(new Geometry("Box", new Box(SCALE/2,SCALE/2,SCALE)),
                (Geometry) testNode.getChild(0));
        assertEquals(new Vector3f(0,0,0.5f), testNode.getChild(0).getLocalTranslation());
    }

    @Test
    public void testAddThree() {
        testSpace.add(new Cell(new Position(1,0,0)));
        testSpace.add(new Cell(new Position(0,0,0)));
        testSpace.add(new Cell(new Position(100,0,0)));
        assertEquals(2, testNode.getChildren().size());
        assertEquals(new Geometry("Box", new Box(SCALE,SCALE/2,SCALE/2)),
                (Geometry) testNode.getChild(0));
        assertEquals(new Vector3f(0.5f,0,0), testNode.getChild(0).getLocalTranslation());
        assertEquals(new Geometry("Box", new Box(SCALE/2,SCALE/2,SCALE/2)),
                (Geometry) testNode.getChild(1));
        assertEquals(new Vector3f(100,0,0), testNode.getChild(1).getLocalTranslation());
    }

    @Test
    public void testAddCube() {
        testSpace.add(new Cell(new Position(1,0,0)));
        testSpace.add(new Cell(new Position(0,0,0)));
        testSpace.add(new Cell(new Position(1,1,0)));
        testSpace.add(new Cell(new Position(0,1,0)));
        testSpace.add(new Cell(new Position(1,0,1)));
        testSpace.add(new Cell(new Position(0,0,1)));
        testSpace.add(new Cell(new Position(1,1,1)));
        testSpace.add(new Cell(new Position(0,1,1)));
        assertEquals(1, testNode.getChildren().size());
        assertEquals(new Geometry("Box", new Box(SCALE,SCALE,SCALE)),
                (Geometry) testNode.getChild(0));
        assertEquals(new Vector3f(0.5f,0.5f,0.5f), testNode.getChild(0).getLocalTranslation());
    }
}
