package inventory;

import cells.Player;
import geometry.Position;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An inventory that can contain InventoryItems's
 */
public class Inventory {
    private Player player;
    private int maxSize;
    @Getter private int selectedSlot;
    private List<InventoryItem> inventory;

    /**
     * Create a new inventory of the given maxSize and 0 as the selected slot.
     * Also fills all slots with the default item
     */
    public Inventory(Player player, int maxSize) throws RuntimeException {
        if (maxSize < 0) {
            throw new RuntimeException("Inventory maxSize must be nonnegative");
        }

        this.player = player;
        this.maxSize = maxSize;
        this.selectedSlot = 0;
        this.inventory = new ArrayList<>(maxSize + 1);
    }

    /**
     * @return maxSize of this inventory
     */
    public int getInventorySize() {
        return maxSize;
    }

    /**
     * Maybe return the inventory item at the given slot.
     * If 0 &lt slot &lt INVENTORY_SIZE, slot is forcefully made to be inside this range
     * @param slot slot of the inventory item
     * @return inventory item at the given slot
     */
    @NotNull
    public Optional<InventoryItem> getInventoryItem(int slot) {
        if (slot >= size()) {
            return Optional.empty();
        }

        return Optional.of(inventory.get(getSafeSlot(slot)));
    }

    /**
     * Produce the name of the item at the given slot.
     * If there is no item, return empty string.
     * @param slot slot of the inventory item
     * @return name of the inventory item or an empty string
     */
    public String getName(int slot) {
        slot = getSafeSlot(slot);

        if (slot < size() && inventory.get(slot) != null) {
            return inventory.get(slot).getName();
        } else {
            return "";
        }
    }

    /**
     * Sets inventory item at the given slot, overriding one if it exists.
     * If 0 < slot < INVENTORY_SIZE, slot is forcefully made to be inside this range
     */
    public void setInventoryItem(int slot, InventoryItem inventoryItem) {
        inventory.set(getSafeSlot(slot), inventoryItem);
    }

    /**
     * @return selected inventory item
     */
    public Optional<InventoryItem> getSelectedItem() {
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
            return maxSize + (slot % maxSize);
        } else {
            return (slot % maxSize);
        }
    }

    /**
     * Produce number of items in this inventory
     * @return number of items in this inventory
     */
    public int size() {
        return inventory.size();
    }

    /**
     * If size() is less then maxSize, add the given item to the inventory,
     * otherwise do nothing
     * @param inventoryItem inventory item to be added
     */
    public void add(InventoryItem inventoryItem) {
        if (this.size() < this.maxSize) {
            inventory.add(inventoryItem);
        }
    }

    public void useSelectedItem() {
        getSelectedItem().ifPresent(i -> i.use(
                player.getSelectedBlockFace(),
                player.getSelectedBlock(),
                player.getPosition()));
    }
}
