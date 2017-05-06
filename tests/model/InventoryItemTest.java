package model;

import model.InventoryItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryItemTest {
    private String testString;

    private InventoryItem item1;
    private InventoryItem item2;

    @Before
    public void runBefore() {
        testString = "M";
        item1 = new InventoryItem("A", () -> testString = "X");
        item2 = new InventoryItem("B", () -> testString = "Y");
    }

    @Test
    public void testConstructor() {
        assertEquals("A", item1.getName());
        assertEquals("B", item2.getName());

        assertEquals("M", testString);
        item1.use();
        assertEquals("X", testString);
        item2.use();
        assertEquals("Y", testString);
        item1.use();
        assertEquals("X", testString);
    }
}
