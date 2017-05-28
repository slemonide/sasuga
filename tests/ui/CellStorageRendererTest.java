package ui;

import com.jme3.scene.Node;
import geometry.Parallelepiped;
import geometry.Position;
import org.junit.Before;
import org.junit.Test;
import world.CellParallelepiped;
import world.CellStorage;
import world.HashMapCellStorage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static cells.StaticCell.*;
import static org.junit.Assert.*;

public class CellStorageRendererTest {
    private CellStorage cellStorage;
    private CellStorageRenderer renderer;
    private Node node;

    // -----------------------------------------------------------------------
    // CONSTANTS
    public static final CellParallelepiped CELL_PARALLELEPIPED_A =
            new CellParallelepiped(
                    new Parallelepiped(
                            new Position(983521002, 2, 3),
                            3, 1, 7),
                    WOOD);
    public static final CellParallelepiped CELL_PARALLELEPIPED_B =
            new CellParallelepiped(
                    new Parallelepiped(
                            new Position(-1, 200, 3),
                            40, 50, 600),
                    STONE);
    public static final CellParallelepiped CELL_PARALLELEPIPED_C =
            new CellParallelepiped(
                    new Parallelepiped(
                            new Position(-2002349001, 2, 3),
                            1, 500, 1),
                    DIRT);
    public static final CellParallelepiped CELL_PARALLELEPIPED_D =
            new CellParallelepiped(
                    new Parallelepiped(
                            new Position(1, 31003213, 354300001),
                            4, 513213, 600),
                    DIRT);
    public static final CellParallelepiped CELL_PARALLELEPIPED_E =
            new CellParallelepiped(
                    new Parallelepiped(
                            new Position(100, -83245329, 3),
                            423, 5, 6),
                    WOOL);
    public static final List<CellParallelepiped> CELL_PARALLELEPIPEDS = Collections.unmodifiableList(Arrays.asList(
            CELL_PARALLELEPIPED_A,
            CELL_PARALLELEPIPED_B,
            CELL_PARALLELEPIPED_C,
            CELL_PARALLELEPIPED_D,
            CELL_PARALLELEPIPED_E
    ));

    // -----------------------------------------------------------------------
    // TESTS

    @Before
    public void runBefore() {
        cellStorage = new HashMapCellStorage();
        renderer = new CellStorageRenderer(cellStorage);
        node = renderer.getNode();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, renderer.size());
        assertEquals(0, renderer.volume());
        assertEquals(0, node.getQuantity());
        assertEquals(node, renderer.getNode());
    }

    /**
     * Note: doesn't cover collisions
     */
    @Test
    public void testConstructorFromFilledStorageBasic() {
        CellParallelepiped cellParallelepiped =
                new CellParallelepiped(
                        new Parallelepiped(
                                new Position(1, 2, 3),
                                4, 5, 6),
                        WOOD);

        cellStorage = new HashMapCellStorage(Collections.singleton(cellParallelepiped));
        renderer = new CellStorageRenderer(cellStorage);
        node = renderer.getNode();

        assertEquals(1, renderer.size());
        assertEquals(cellParallelepiped.parallelepiped.volume(), renderer.volume());
        assertEquals(1, node.getQuantity());
        assertEquals(node, renderer.getNode());
    }

    /**
     * Note: doesn't cover collisions
     */
    @Test
    public void testConstructorFromFilledStorageHard() {
        cellStorage = new HashMapCellStorage(CELL_PARALLELEPIPEDS);
        renderer = new CellStorageRenderer(cellStorage);
        node = renderer.getNode();

        assertEquals(CELL_PARALLELEPIPEDS.size(), renderer.size());
        assertEquals(CELL_PARALLELEPIPEDS.parallelStream().mapToInt(CellParallelepiped::volume).sum(),
                renderer.volume());
        assertEquals(CELL_PARALLELEPIPEDS.size(), node.getQuantity());
        assertEquals(node, renderer.getNode());
    }

    /**
     * Tests constructor with the same entries added many times
     *
     * Note: doesn't cover collisions
     * @see #testConstructorFromFilledStorageHard
     */
    @Test
    public void testConstructorFromFilledStorageHardReperative() {
        cellStorage = new HashMapCellStorage(Arrays.asList(
                CELL_PARALLELEPIPED_A,
                CELL_PARALLELEPIPED_B,
                CELL_PARALLELEPIPED_D,
                CELL_PARALLELEPIPED_B,
                CELL_PARALLELEPIPED_C,
                CELL_PARALLELEPIPED_D,
                CELL_PARALLELEPIPED_D,
                CELL_PARALLELEPIPED_B,
                CELL_PARALLELEPIPED_B,
                CELL_PARALLELEPIPED_E,
                CELL_PARALLELEPIPED_A,
                CELL_PARALLELEPIPED_B,
                CELL_PARALLELEPIPED_D,
                CELL_PARALLELEPIPED_E
        ));
        renderer = new CellStorageRenderer(cellStorage);
        node = renderer.getNode();

        assertEquals(5, renderer.size());
        assertEquals(CELL_PARALLELEPIPEDS.parallelStream().mapToInt(CellParallelepiped::volume).sum(),
                renderer.volume());
        assertEquals(5, node.getQuantity());
        assertEquals(node, renderer.getNode());
    }

    @Test
    public void testRender() {
        renderer.render();

        assertEquals(0, renderer.size());
        assertEquals(0, renderer.volume());
        assertEquals(0, node.getQuantity());
        assertEquals(node, renderer.getNode());

        for (int i = 0; i < CELL_PARALLELEPIPEDS.size(); i++) {
            cellStorage.add(CELL_PARALLELEPIPEDS.get(i));

            assertEquals(i, renderer.size());
            assertEquals(CELL_PARALLELEPIPEDS.parallelStream().limit(i).mapToInt(CellParallelepiped::volume).sum(),
                    renderer.volume());
            assertEquals(i, node.getQuantity());
            assertEquals(node, renderer.getNode());

            renderer.render();

            assertEquals(i + 1, renderer.size());
            assertEquals(CELL_PARALLELEPIPEDS.parallelStream().limit(i + 1).mapToInt(CellParallelepiped::volume).sum(),
                    renderer.volume());
            assertEquals(i + 1, node.getQuantity());
            assertEquals(node, renderer.getNode());

        }
    }

    @Test
    public void testRenderAddTwice() {
        cellStorage.add(CELL_PARALLELEPIPED_B);

        assertEquals(0, renderer.size());
        assertEquals(0, renderer.volume());
        assertEquals(node, renderer.getNode());

        renderer.render();

        assertEquals(1, renderer.size());
        assertEquals(CELL_PARALLELEPIPED_B.volume(), renderer.volume());
        assertEquals(node, renderer.getNode());

        // Adding second time

        cellStorage.add(CELL_PARALLELEPIPED_B);

        assertEquals(1, renderer.size());
        assertEquals(CELL_PARALLELEPIPED_B.volume(), renderer.volume());
        assertEquals(node, renderer.getNode());

        renderer.render();

        assertEquals(1, renderer.size());
        assertEquals(CELL_PARALLELEPIPED_B.volume(), renderer.volume());
        assertEquals(node, renderer.getNode());
    }
}
