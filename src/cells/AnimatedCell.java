package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;
import inventory.InventoryItem;
import org.jetbrains.annotations.NotNull;
import world.World;

/**
 * A cell whose state is known for any given tick
 */
public enum AnimatedCell implements InventoryItem, WorldCell {
    OSCILLATOR("Oscillator", (tick) -> {
        if (tick % 2 == 0) {
            return ColorRGBA.Magenta;
        } else {
            return ColorRGBA.Yellow;
        }
    });

    private final String name;
    private final Function function;

    /**
     * A color function of tick and position
     */
    private interface Function { ColorRGBA apply(int tick); }

    /**
     * Create an animated cell with given name and a color function
     * @param name name of the animated cell
     * @param function color generating function
     */
    AnimatedCell(String name, Function function) {
        this.name = name;
        this.function = function;
    }

    /**
     * Produce the name of this animated cell
     * @return name of this animated cell
     */
    @NotNull
    @Override
    public String getName() {
        return name;
    }

    /**
     * Produce the color of this animated cell from the given tick and position
     * @param tick current tick
     * @return current color of this animated cell
     */
    @NotNull
    @Override
    public ColorRGBA getColor(int tick, Position position) {
        return function.apply(tick);
    }

    @Override
    public void use(Position placeCursor, Position removeCursor, Position player) {
        World.getInstance().add(placeCursor, this);
    }
}
