package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;
import inventory.InventoryItem;
import world.World;

/**
 * A cell that never changes
 */
public enum StaticCell implements InventoryItem, VisibleCell {
    STONE("Stone", ColorRGBA.Gray),
    DIRT("Dirt", ColorRGBA.Orange),
    GRASS("Grass", ColorRGBA.Green),
    WOOD("Wood", ColorRGBA.Brown);

    private final String name;
    private final ColorRGBA color;

    StaticCell(String name, ColorRGBA color) {

        this.name = name;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    public ColorRGBA getColor() {
        return color;
    }

    @Override
    public void use(Position placeCursor, Position removeCursor, Position player) {
        // TODO: remove code duplication with AnimatedCell. How?
        World.getInstance().add(placeCursor, this);
    }

    @Override
    public ColorRGBA getColor(int tick, Position position) {
        return getColor();
    }
}
