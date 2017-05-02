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
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,1,1)));
    }

    @Test
    public void testAddTwoY() {
        testSpace.add(new Position(0,1,0));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,2,1)));
    }

    @Test
    public void testAddTwoZ() {
        testSpace.add(new Position(0,0,1));
        testSpace.add(new Position(0,0,0));
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                1,1,2)));
    }

    @Test
    public void testAddThree() {
        testSpace.add(new Position(1,0,0));
        testSpace.add(new Position(0,0,0));
        testSpace.add(new Position(100,0,0));
        assertEquals(2, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,1,1)));
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(100,0,0))));
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
        assertEquals(1, testSpace.size());
        assertTrue(testSpace.getParallelepipeds().contains(new Parallelepiped(new Position(0,0,0),
                2,2,2)));
    }
}
