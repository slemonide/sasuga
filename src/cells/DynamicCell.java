package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;
import inventory.InventoryItem;
import org.jetbrains.annotations.NotNull;

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
        return "";
    }

    @Override
    public void use(Position placeCursor, Position removeCursor, Position player) {

    }

    @NotNull
    @Override
    public ColorRGBA getColor(int tick, Position position) {
        return ColorRGBA.Black;
    }
    // TODO: finish
}
