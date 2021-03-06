package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;
import inventory.InventoryItem;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import world.World;

/**
 * A cell that never changes
 */
public enum StaticCell implements InventoryItem, WorldCell {
    STONE("Stone", ColorRGBA.Gray),
    DIRT("Dirt", ColorRGBA.Orange),
    GRASS("Grass", ColorRGBA.Green),
    WOOD("Wood", ColorRGBA.Brown),
    WOOL("Wool", ColorRGBA.White);

    @Getter
    private final String name;
    @Getter
    private final ColorRGBA color;

    StaticCell(String name, ColorRGBA color) {

        this.name = name;
        this.color = color;
    }

    @Override
    public void use(Position placeCursor, Position removeCursor, Position player) {
        // TODO: remove code duplication with AnimatedCell. How?
        World.getInstance().add(placeCursor, this);
    }

    @NotNull
    @Override
    public ColorRGBA getColor(int tick, Position position) {
        return getColor();
    }
}
