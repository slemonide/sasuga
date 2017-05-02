package geometry;

import model.Position;
import org.jetbrains.annotations.Contract;

/**
 * Represents a right-angled parallelepiped with integer-valued side lengths
 */
public final class Parallelepiped {
    private final Position center;
    private final int xSize;
    private final int ySize;
    private final int zSize;

    /**
     * Creates a unit parallelepiped centered at the given position
     * @param center center position of the parallelepiped
     */
    public Parallelepiped(Position center) {
        this.center = center;
        this.xSize = 1;
        this.ySize = 1;
        this.zSize = 1;
    }

    public Parallelepiped(Position center, int xSize, int ySize, int zSize) {
        this.center = center;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
    }

    @Contract(pure = true)
    public Position getCenter() {
        return center;
    }

    @Contract(pure = true)
    public int getXSize() {
        return xSize;
    }

    @Contract(pure = true)
    public int getYSize() {
        return ySize;
    }

    @Contract(pure = true)
    public int getZSize() {
        return zSize;
    }

    @Contract(pure = true)
    public boolean contains(Position position) {
        return ((xSize/2 - xSize + 1 + center.x <= position.x) && (position.x <= xSize/2 + center.x) &&
                (ySize/2 - ySize + 1 + center.y <= position.y) && (position.y <= ySize/2 + center.y) &&
                (zSize/2 - zSize + 1 + center.z <= position.z) && (position.z <= zSize/2 + center.z));
    }
}
