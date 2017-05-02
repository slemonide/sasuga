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
    public int getSize(Dimension dimension) {
        switch (dimension) {
            case X:
                return xSize;
            case Y:
                return ySize;
            default:
                return zSize;
        }
    }

    @Contract(pure = true)
    public boolean contains(Position position) {
        return ((xSize/2 - xSize + 1 + center.x <= position.x) && (position.x <= xSize/2 + center.x) &&
                (ySize/2 - ySize + 1 + center.y <= position.y) && (position.y <= ySize/2 + center.y) &&
                (zSize/2 - zSize + 1 + center.z <= position.z) && (position.z <= zSize/2 + center.z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parallelepiped that = (Parallelepiped) o;

        return xSize == that.xSize && ySize == that.ySize && zSize == that.zSize && center.equals(that.center);
    }

    @Override
    public int hashCode() {
        int result = center.hashCode();
        result = 31 * result + xSize;
        result = 31 * result + ySize;
        result = 31 * result + zSize;
        return result;
    }

    public Parallelepiped setCenter(Position center) {
        return new Parallelepiped(center, xSize, ySize, zSize);
    }

    public Parallelepiped setSize(Dimension dimension, int size) {
        switch (dimension) {
            case X:
                return new Parallelepiped(center, size, ySize, zSize);
            case Y:
                return new Parallelepiped(center, xSize, size, zSize);
            default:
                return new Parallelepiped(center, xSize, ySize, size);
        }
    }
}
