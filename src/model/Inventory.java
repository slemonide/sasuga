package model;

import java.util.ArrayList;
import java.util.List;

/**
 * An inventory that can contain InventoryItems's
 */
public class Inventory {
    public static final InventoryItem DEFAULT_INVENTORY_ITEM = new InventoryItem("...", null);
    private int size;
    private int selectedSlot;
    private List<InventoryItem> inventory;

    /**
     * Create a new inventory of the given size and 0 as the selected slot.
     * Also fills all slots with the default item
     */
    public Inventory(int size) throws Exception {
        if (size < 0) {
            throw new Exception("Inventory size must be nonnegative");
        }

        this.size = size;
        this.selectedSlot = 0;
        this.inventory = new ArrayList<>();

        fillInventory();
    }

    private void fillInventory() {
        for (int i = 0; i < size; i++) {
            inventory.add(DEFAULT_INVENTORY_ITEM);
        }
    }

    /**
     * @return size of this inventory
     */
    public int getInventorySize() {
        return size;
    }

    /**
     * Returns the inventory item at the given slot.
     * If 0 < slot < INVENTORY_SIZE, slot is forcefully made to be inside this range
     */
    public InventoryItem getInventoryItem(int slot) {
        return inventory.get(getSafeSlot(slot));
    }

    /**
     * Sets inventory item at the given slot, overriding one if it exists.
     * If 0 < slot < INVENTORY_SIZE, slot is forcefully made to be inside this range
     */
    public void setInventoryItem(int slot, InventoryItem inventoryItem) {
        inventory.set(getSafeSlot(slot), inventoryItem);
    }

    /**
     * @return selected slot
     */
    public int getSelectedSlot() {
        return selectedSlot;
    }

    /**
     * @return selected inventory item
     */
    public InventoryItem getSelectedItem() {
        return getInventoryItem(getSelectedSlot());
    }

    /**
     * Sets the given slot as the selected inventory slot.
     * If 0 < slot < INVENTORY_SIZE, slot is forcefully made to be inside this range
     */
    public void setSelectedSlot(int slot) {
        selectedSlot = getSafeSlot(slot);
    }

    private int getSafeSlot(int slot) {
        if (slot < 0) {
            return getInventorySize() + (slot % getInventorySize());
        } else {
            return (slot % getInventorySize());
        }
    }
}
