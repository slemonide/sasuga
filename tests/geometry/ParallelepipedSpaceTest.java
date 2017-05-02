package geometry;

import com.jme3.scene.Node;
import model.Cell;
import model.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelepipedSpaceTest {
    Node testNode;
    ParallelepipedSpace testSpace;

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

    }
}
