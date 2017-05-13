package inventory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {
    private static final int INVENTORY_SIZE = 10;
    private Inventory testInventory;

    @Before
    public void runBefore() throws Exception {
        testInventory = new Inventory(INVENTORY_SIZE);
    }

    @Test
    public void testConstructor() throws Exception {
        assertEquals(INVENTORY_SIZE, testInventory.getInventorySize());
        assertEquals(0, testInventory.getSelectedSlot());

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            assertEquals(Inventory.DEFAULT_INVENTORY_ITEM, testInventory.getInventoryItem(i));
        }

        testInventory = new Inventory(2);
        assertEquals(2, testInventory.getInventorySize());
    }

    @Test(expected = Exception.class)
    public void testConstructorInvalidSize() throws Exception {
        new Inventory(-1);
    }

    @Test(expected = Exception.class)
    public void testConstructorInvalidSizeAnotherOne() throws Exception {
        new Inventory(-100);
    }

    @Test
    public void testConstructorZeroSize() throws Exception {
        testInventory = new Inventory(0);

        assertEquals(0, testInventory.getInventorySize());
    }

    @Test
    public void testSelectedSlot() {
        fillInventory(testInventory);
        testInventory.setSelectedSlot(1);
        assertEquals("1", testInventory.getSelectedItem().getName());

        testInventory.setSelectedSlot(6);
        assertEquals("6", testInventory.getSelectedItem().getName());
    }

    /**
     * Fills inventory with test items
     */
    private void fillInventory(Inventory inventory) {
        for (int i = 0; i < inventory.getInventorySize(); i++) {
            inventory.setInventoryItem(i, new InventoryItem(Integer.toString(i), null));
        }
    }

    private void checkInventoryFilling(Inventory inventory) {
        for (int i = 0; i < inventory.getInventorySize(); i++) {
            assertEquals(Integer.toString(i), inventory.getInventoryItem(i).getName());
        }
    }

    @Test
    public void testInventoryFilling() {
        fillInventory(testInventory);
        checkInventoryFilling(testInventory);
    }

    @Test
    public void testInventorySlotOverflow() {
        testInventory.setSelectedSlot(testInventory.getInventorySize() + 5);
        assertEquals(5, testInventory.getSelectedSlot());

        testInventory.setSelectedSlot(-testInventory.getInventorySize() + 3);
        assertEquals(3, testInventory.getSelectedSlot());
    }
}
