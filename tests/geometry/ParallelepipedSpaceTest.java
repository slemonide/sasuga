package geometry;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import model.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelepipedSpaceTest {
    private ParallelepipedSpace testSpace;

    @Before
    public void runBefore() {
        testSpace = new ParallelepipedSpace();
    }

    @Test
    public void testConstructor() {
        assertTrue(testSpace.isEmpty());
        assertEquals(0, testSpace.size());
    }

    @Test
    public void testSimpleAdd() {
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testAddTwice() {
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testAddTwoX() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0))));
    }

    @Test
    public void testAddTwoY() {
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,0,0));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,2,1)));
    }
/*
    @Test
    public void testAddTwoZ() {
        testSpace.add(new Position(0,0,1));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testNode.getChildren().size());
        assertEquals(new Geometry("Box", new Box(SCALE/2,SCALE/2,SCALE)),
                (Geometry) testNode.getChild(0));
        assertEquals(new Vector3f(0,0,0.5f), testNode.getChild(0).getLocalTranslation());
    }

    @Test
    public void testAddThree() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(100,0,0));
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
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(1,1,0));
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(1,0,1));
        testSpace.add(new Position(0,0,1));
        testSpace.add(new Position(1,1,1));
        testSpace.add(new Position(0,1,1));
        assertEquals(1, testNode.getChildren().size());
        assertEquals(new Geometry("Box", new Box(SCALE,SCALE,SCALE)),
                (Geometry) testNode.getChild(0));
        assertEquals(new Vector3f(0.5f,0.5f,0.5f), testNode.getChild(0).getLocalTranslation());
    }*/
}
