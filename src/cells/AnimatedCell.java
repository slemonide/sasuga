package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;
import inventory.InventoryItem;
import world.World;

/**
 * A cell whose state is known for any given tick
 */
public enum AnimatedCell implements InventoryItem, VisibleCell {
    OSCILLATOR("Oscillator", (tick, position) -> {
        if (tick % 2 == 0) {
            return ColorRGBA.Magenta;
        } else {
            return ColorRGBA.Yellow;
        }
    });

    private final String name;
    private final F function;

    /**
     * A color function of tick and position
     */
    interface F { ColorRGBA f(int tick, Position position); }

    /**
     * Create an animated cell with given name and a color function
     * @param name name of the animated cell
     * @param function color generating function
     */
    AnimatedCell(String name, F function) {
        this.name = name;
        this.function = function;
    }

    /**
     * Produce the name of this animated cell
     * @return name of this animated cell
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Produce the color of this animated cell from the given tick and position
     * @param tick current tick
     * @param position position of this animated cell
     * @return current color of this animated cell
     */
    @Override
    public ColorRGBA getColor(int tick, Position position) {
        return function.f(tick, position);
    }

    @Override
    public void use(Position placeCursor, Position removeCursor, Position player) {
        World.getInstance().add(placeCursor, this);
    }
}
