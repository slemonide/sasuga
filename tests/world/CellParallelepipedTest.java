package world;

import geometry.Parallelepiped;
import geometry.Position;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static cells.StaticCell.STONE;
import static cells.StaticCell.WOOD;
import static cells.StaticCell.WOOL;

import static org.junit.Assert.*;

public class CellParallelepipedTest {
    private CellParallelepiped testCP;

    @Test
    public void testPositionConstructor() {
        testCP = new CellParallelepiped(new Position(0,0,0), WOOL);

        Assert.assertEquals(testCP.cell, WOOL);
        // TODO: make position be a kind of parallelepiped (parallelepiped extends position?)
        assertEquals(testCP.parallelepiped, new Parallelepiped(new Position(0,0,0)));
    }

    @Test
    public void testParallelepipedConstructorBasic() {
        testCP = new CellParallelepiped(new Parallelepiped(new Position(0,0,0)), STONE);

        Assert.assertEquals(testCP.cell, STONE);
        assertEquals(testCP.parallelepiped, new Parallelepiped(new Position(0,0,0)));
    }

    @Test
    public void testParallelepipedConstructorHarder() {
        testCP = new CellParallelepiped(new Parallelepiped(new Position(0,0,0), 10, 20, 30), WOOD);

        Assert.assertEquals(testCP.cell, WOOD);
        assertEquals(testCP.parallelepiped, new Parallelepiped(new Position(0,0,0), 10, 20,30));
    }

    @Test
    public void testEquals() {
        assertEquals(new CellParallelepiped(new Parallelepiped(new Position(0,0,0),1, 2, 10), WOOD),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0),1, 2, 10), WOOD));
    }

    @Test
    public void testNotEqualsSize() {
        assertNotEquals(new CellParallelepiped(new Parallelepiped(new Position(0,0,0),1, 3, 10), WOOD),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0),1, 2, 10), WOOD));
    }

    @Test
    public void testNotEqualsMaterial() {
        assertNotEquals(new CellParallelepiped(new Parallelepiped(new Position(0,0,0),1, 2, 10), STONE),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0),1, 2, 10), WOOD));
    }

    @Test
    public void testNotEqualsPosition() {
        assertNotEquals(new CellParallelepiped(new Parallelepiped(new Position(3,0,0),1, 2, 10), WOOD),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0),1, 2, 10), WOOD));
    }

    @Test
    public void testHashCode() {
        Set<CellParallelepiped> cellParallelepipedSet =
                new HashSet<>(Collections.singleton(new CellParallelepiped(
                        new Parallelepiped(new Position(3,0,0),1, 2, 10), WOOD)));

        assertTrue(cellParallelepipedSet.contains(new CellParallelepiped(
                new Parallelepiped(new Position(3,0,0),1, 2, 10), WOOD)));
    }
}
