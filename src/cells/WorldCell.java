package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;
import org.jetbrains.annotations.NotNull;

/**
 * A cell that can be placed into the world
 * <p>All implementations of this interface should be static</p>
 */
public interface WorldCell {
    @NotNull
    ColorRGBA getColor(int tick, Position position);
}
