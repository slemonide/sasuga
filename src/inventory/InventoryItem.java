package inventory;

import geometry.Position;

/**
 * An item that can be stored in the inventory
 */
public interface InventoryItem {

    String getName();
    void use(Position placeCursor, Position removeCursor, Position player);
}
