package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;
import inventory.InventoryItem;

/**
 * A cell that modifies the world actively
 * <p>
 *     CellParallelepiped state is hard to compute for every given tick
 * </p>
 */
public enum DynamicCell implements InventoryItem, WorldCell {
    ;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void use(Position placeCursor, Position removeCursor, Position player) {

    }

    @Override
    public ColorRGBA getColor(int tick, Position position) {
        return null;
    }
    // TODO: finish
}
