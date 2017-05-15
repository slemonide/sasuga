package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;

/**
 * A cell that can be placed into the world
 * <p>All implementations of this interface should be static</p>
 */
public interface VisibleCell {
    ColorRGBA getColor(int tick, Position position);
}
