package world;

import cells.StaticCell;
import geometry.Parallelepiped;
import geometry.Position;
import org.junit.Before;
import org.junit.Test;
import ui.CellStorageRendererTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static cells.StaticCell.*;
import static org.junit.Assert.*;
import static ui.CellStorageRendererTest.CELL_PARALLELEPIPEDS;

/**
 * Tests for the HashMapCellStorage class
 */
public class HashMapCellStorageTest {
    private HashMapCellStorage testHashMapCellStorage = new HashMapCellStorage();

    @Before
    public void runBefore() {
        testHashMapCellStorage.clear();
    }

    @Test
    public void testConstructor() {
        assertTrue(testHashMapCellStorage.isEmpty());
        assertEquals(0, testHashMapCellStorage.size());
    }

    /* Behaves badly because there are multiple constructors
    @Test(expected = NullPointerException.class)
    public void testConstructorNullPointerExceptionThrown() {
        new HashMapCellStorage(null);
    }*/

    @Test
    public void testConstructorNullPointerExceptionNotThrown() {
        try {
            new HashMapCellStorage(new HashSet<>());
        } catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    public void testConstructorCollectionOneElement() {
        testHashMapCellStorage = new HashMapCellStorage(Collections.singleton(new CellParallelepiped(new Position(0,0,0),
                StaticCell.WOOD)));
        assertFalse(testHashMapCellStorage.isEmpty());
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertEquals(1, testHashMapCellStorage.cellParallelepipeds().size());
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(new Position(0,0,0),
                StaticCell.WOOD)));
    }

    @Test
    public void testConstructorCollectionTwoElementsX() {
        testHashMapCellStorage = new HashMapCellStorage(Arrays.asList(
                new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD),
                new CellParallelepiped(new Position(1,0,0), StaticCell.WOOD)));
        assertFalse(testHashMapCellStorage.isEmpty());
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.contains(new Position(1,0,0)));
        assertEquals(1, testHashMapCellStorage.cellParallelepipeds().size());
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(0,0,0), 2,1,1),StaticCell.WOOD)));
    }

    @Test
    public void testConstructorCollectionGeneralCase() {
        testHashMapCellStorage = new HashMapCellStorage(CELL_PARALLELEPIPEDS);
        assertFalse(testHashMapCellStorage.isEmpty());
        assertEquals(CELL_PARALLELEPIPEDS.size(), testHashMapCellStorage.size());

        for (CellParallelepiped cellParallelepiped : CELL_PARALLELEPIPEDS) {
            assertTrue(testHashMapCellStorage.contains(cellParallelepiped.parallelepiped.getCorner()));
            assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(cellParallelepiped));
        }
        assertEquals(CELL_PARALLELEPIPEDS.size(), testHashMapCellStorage.cellParallelepipeds().size());
    }

    @Test
    public void testEqualsRegularOrder() {
        CellStorage cellStorageA = new HashMapCellStorage();
        cellStorageA.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,0,1), StaticCell.STONE));
        cellStorageA.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));

        CellStorage cellStorageB = new HashMapCellStorage();
        cellStorageB.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageB.add(new CellParallelepiped(new Position(0,0,1), StaticCell.STONE));
        cellStorageB.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageB.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));

        assertEquals(cellStorageA, cellStorageB);
    }

    @Test
    public void testEqualsDifferentOrder() {
        CellStorage cellStorageA = new HashMapCellStorage();
        cellStorageA.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,0,1), StaticCell.STONE));
        cellStorageA.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));

        CellStorage cellStorageB = new HashMapCellStorage();
        cellStorageB.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageB.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageB.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));
        cellStorageB.add(new CellParallelepiped(new Position(0,0,1), StaticCell.STONE));

        assertEquals(cellStorageA, cellStorageB);
    }

    @Test
    public void testNotEquals() {
        CellStorage cellStorageA = new HashMapCellStorage();
        cellStorageA.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,0,1), StaticCell.STONE));
        cellStorageA.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));

        CellStorage cellStorageB = new HashMapCellStorage();
        cellStorageB.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageB.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageB.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));
        cellStorageB.add(new CellParallelepiped(new Position(0,0,1), StaticCell.WOOD));

        assertNotEquals(cellStorageA, cellStorageB);
    }

    @Test
    public void testHashCode() {
        CellStorage cellStorageA = new HashMapCellStorage();
        cellStorageA.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,0,1), StaticCell.STONE));
        cellStorageA.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));

        CellStorage cellStorageB = new HashMapCellStorage();
        cellStorageB.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageB.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageB.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));
        cellStorageB.add(new CellParallelepiped(new Position(0,0,1), StaticCell.WOOD));

        Set<CellStorage> cellStorageSet = new HashSet<>();
        assertFalse(cellStorageSet.contains(cellStorageA));
        assertFalse(cellStorageSet.contains(cellStorageB));

        cellStorageSet.add(cellStorageA);

        assertTrue(cellStorageSet.contains(cellStorageA));
        assertFalse(cellStorageSet.contains(cellStorageB));

        cellStorageSet.add(cellStorageB);

        assertTrue(cellStorageSet.contains(cellStorageA));
        assertTrue(cellStorageSet.contains(cellStorageB));
    }

    @Test
    public void testConstructorFromAnotherInstance() {
        CellStorage cellStorageA = new HashMapCellStorage();
        cellStorageA.add(new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,0,1), StaticCell.STONE));
        cellStorageA.add(new CellParallelepiped(new Position(10,0,0), StaticCell.WOOD));
        cellStorageA.add(new CellParallelepiped(new Position(0,-30,10), StaticCell.DIRT));

        CellStorage cellStorageB = new HashMapCellStorage(cellStorageA);

        assertEquals(cellStorageA, cellStorageB);
    }

    @Test
    public void testConstructorCollectionTwoElementsY() {
        testHashMapCellStorage = new HashMapCellStorage(Arrays.asList(
                new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD),
                new CellParallelepiped(new Position(0,1,0), StaticCell.WOOD)));
        assertFalse(testHashMapCellStorage.isEmpty());
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.contains(new Position(0,1,0)));
        assertEquals(1, testHashMapCellStorage.cellParallelepipeds().size());
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(0,0,0), 1,2,1),StaticCell.WOOD)));
    }

    @Test
    public void testConstructorCollectionTwoElementsZ() {
        testHashMapCellStorage = new HashMapCellStorage(Arrays.asList(
                new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD),
                new CellParallelepiped(new Position(0,0,1), StaticCell.WOOD)));
        assertFalse(testHashMapCellStorage.isEmpty());
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,1)));
        assertEquals(1, testHashMapCellStorage.cellParallelepipeds().size());
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(0,0,0), 1,1,2),StaticCell.WOOD)));
    }

    // different material

    @Test
    public void testConstructorCollectionTwoElementsDifferentMaterialX() {
        testHashMapCellStorage = new HashMapCellStorage(Arrays.asList(
                new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD),
                new CellParallelepiped(new Position(1,0,0), StaticCell.STONE)));
        assertFalse(testHashMapCellStorage.isEmpty());
        assertEquals(2, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.contains(new Position(1,0,0)));
        assertEquals(2, testHashMapCellStorage.cellParallelepipeds().size());
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(0,0,0), 1,1,1),StaticCell.WOOD)));
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(1,0,0), 1,1,1),StaticCell.STONE)));
    }

    @Test
    public void testConstructorCollectionTwoElementsDifferentMaterialsY() {
        testHashMapCellStorage = new HashMapCellStorage(Arrays.asList(
                new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD),
                new CellParallelepiped(new Position(0,1,0), StaticCell.STONE)));
        assertFalse(testHashMapCellStorage.isEmpty());
        assertEquals(2, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.contains(new Position(0,1,0)));
        assertEquals(2, testHashMapCellStorage.cellParallelepipeds().size());
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(0,0,0), 1,1,1),StaticCell.WOOD)));
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(0,1,0), 1,1,1),StaticCell.STONE)));
    }

    @Test
    public void testConstructorCollectionTwoElementsDifferentMaterialsZ() {
        testHashMapCellStorage = new HashMapCellStorage(Arrays.asList(
                new CellParallelepiped(new Position(0,0,0), StaticCell.WOOD),
                new CellParallelepiped(new Position(0,0,1), StaticCell.STONE)));
        assertFalse(testHashMapCellStorage.isEmpty());
        assertEquals(2, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,1)));
        assertEquals(2, testHashMapCellStorage.cellParallelepipeds().size());
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(0,0,0), 1,1,1),StaticCell.WOOD)));
        assertTrue(testHashMapCellStorage.cellParallelepipeds().contains(new CellParallelepiped(
                new Parallelepiped(new Position(0,0,1), 1,1,1),StaticCell.STONE)));
    }

    @Test
    public void testGetNothing() {
        assertFalse(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
    }

    @Test
    public void testAddPositionSimple() {
        testHashMapCellStorage.add(new Position(0,0,0), WOOL);
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Position(0,0,0), WOOL));
    }

    @Test
    public void testAddParallelepipedSimple() {
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0)), WOOL);
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Position(0,0,0), WOOL));
    }

    @Test
    public void testAddParallelepipedBig() {
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0), 10, 20, 100), WOOL);
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0), 10, 20, 100), WOOL));
    }

    @Test
    public void testAddSeveralMaterials() {
        testHashMapCellStorage.add(new Position(0,0,0), WOOD);
        testHashMapCellStorage.add(new Position(0,1,0), DIRT);
        assertEquals(2, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Position(0,0,0), WOOD));
        assertTrue(testHashMapCellStorage.get(new Position(0,1,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,1,0)).get(),
                new CellParallelepiped(new Position(0,1,0), DIRT));
    }

    @Test
    public void testAddMaterialMerging() {
        testHashMapCellStorage.add(new Position(0,0,0), WOOL);
        testHashMapCellStorage.add(new Position(0,1,0), WOOL);
        assertEquals(1, testHashMapCellStorage.size());

        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.contains(new Position(0,1,0)));

        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0), 1, 2, 1), WOOL));

        assertTrue(testHashMapCellStorage.get(new Position(0,1,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,1,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0), 1, 2, 1), WOOL));
    }

    @Test
    public void testAddTwiceSameMaterial() {
        testHashMapCellStorage.add(new Position(0,0,0), WOOD);
        testHashMapCellStorage.add(new Position(0,0,0), WOOD);
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Position(0,0,0), WOOD));
    }

    @Test
    public void testAddTwiceDifferentMaterial() {
        testHashMapCellStorage.add(new Position(0,0,0), WOOD);
        testHashMapCellStorage.add(new Position(0,0,0), STONE);
        assertEquals(1, testHashMapCellStorage.size());
        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Position(0,0,0), WOOD));
    }

    @Test
    public void testAddParallelepipedIntersectSameMaterial() {
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0), 10, 1, 1), WOOD);
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0), 1, 10, 1), WOOD);
        assertEquals(2, testHashMapCellStorage.size());

        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0), 10, 1, 1), WOOD));

        assertTrue(testHashMapCellStorage.contains(new Position(0,2,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,2,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,2,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,1,0), 1, 9, 1), WOOD));
    }

    @Test
    public void testAddParallelepipedIntersectDifferentMaterial() {
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0), 10, 1, 1), WOOD);
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0), 1, 10, 1), STONE);
        assertEquals(2, testHashMapCellStorage.size());

        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0), 10, 1, 1), WOOD));

        assertTrue(testHashMapCellStorage.contains(new Position(0,2,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,2,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,2,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,1,0), 1, 9, 1), STONE));
    }

    @Test
    public void testAddParallelepipedIntersectDifferentMaterialMany() {
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0), 10, 1, 1), WOOD);
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0), 1, 10, 1), STONE);
        testHashMapCellStorage.add(new Parallelepiped(new Position(0,0,0), 1, 1, 10), DIRT);
        assertEquals(3, testHashMapCellStorage.size());

        assertTrue(testHashMapCellStorage.contains(new Position(0,0,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,0), 10, 1, 1), WOOD));

        assertTrue(testHashMapCellStorage.contains(new Position(0,2,0)));
        assertTrue(testHashMapCellStorage.get(new Position(0,2,0)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,2,0)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,1,0), 1, 9, 1), STONE));

        assertTrue(testHashMapCellStorage.contains(new Position(0,0,2)));
        assertTrue(testHashMapCellStorage.get(new Position(0,0,2)).isPresent());
        assertEquals(testHashMapCellStorage.get(new Position(0,0,2)).get(),
                new CellParallelepiped(new Parallelepiped(new Position(0,0,1), 1, 1, 9), DIRT));
    }
}
