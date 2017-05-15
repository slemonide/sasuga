package inventory;

import cells.Player;
import geometry.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {
    private static final int INVENTORY_SIZE = 10;
    private static final Player testPlayer = new Player(new Position(0,0,0));
    private Inventory testInventory;

    @Before
    public void runBefore() throws Exception {
        testInventory = new Inventory(testPlayer, INVENTORY_SIZE);
    }

    @Test
    public void testConstructor() throws Exception {
        assertEquals(INVENTORY_SIZE, testInventory.getInventorySize());
        assertEquals(0, testInventory.getSelectedSlot());

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            assertEquals(null, testInventory.getInventoryItem(i));
            assertEquals("", testInventory.getName(i));
        }

        testInventory = new Inventory(testPlayer, 2);
        assertEquals(2, testInventory.getInventorySize());
    }

    @Test(expected = Exception.class)
    public void testConstructorInvalidSize() throws Exception {
        new Inventory(testPlayer, -1);
    }

    @Test(expected = Exception.class)
    public void testConstructorInvalidSizeAnotherOne() throws Exception {
        new Inventory(testPlayer, -100);
    }

    @Test
    public void testConstructorZeroSize() throws Exception {
        testInventory = new Inventory(testPlayer, 0);

        assertEquals(0, testInventory.getInventorySize());
    }

    @Test
    public void testInventorySlotOverflow() {
        testInventory.setSelectedSlot(testInventory.getInventorySize() + 5);
        assertEquals(5, testInventory.getSelectedSlot());

        testInventory.setSelectedSlot(-testInventory.getInventorySize() + 3);
        assertEquals(3, testInventory.getSelectedSlot());
    }
}
