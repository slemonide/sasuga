package inventory;

import geometry.Position;
import org.jetbrains.annotations.NotNull;

/**
 * An item that can be stored in the inventory
 */
public interface InventoryItem {

    String getName();
    void use(Position placeCursor, Position removeCursor, Position player);
}
