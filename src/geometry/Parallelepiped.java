package geometry;

import model.Position;
import org.jetbrains.annotations.Contract;

/**
 * Represents a right-angled parallelepiped with integer-valued side lengths
 */
public final class Parallelepiped {
    private final Position corner;
    private final int xSize;
    private final int ySize;
    private final int zSize;

    /**
     * Creates a unit parallelepiped cornered at the given position
     * @param corner corner position of the parallelepiped
     */
    public Parallelepiped(Position corner) {
        this.corner = corner;
        this.xSize = 1;
        this.ySize = 1;
        this.zSize = 1;
    }

    public Parallelepiped(Position corner, int xSize, int ySize, int zSize) {
        this.corner = corner;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
    }

    @Contract(pure = true)
    public Position getCorner() {
        return corner;
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
        return ((corner.x <= position.x) && (position.x < corner.x + xSize) &&
                (corner.y <= position.y) && (position.y < corner.y + ySize) &&
                (corner.z <= position.z) && (position.z < corner.z + zSize));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parallelepiped that = (Parallelepiped) o;

        return xSize == that.xSize && ySize == that.ySize && zSize == that.zSize && corner.equals(that.corner);
    }

    @Override
    public int hashCode() {
        int result = corner.hashCode();
        result = 31 * result + xSize;
        result = 31 * result + ySize;
        result = 31 * result + zSize;
        return result;
    }

    public Parallelepiped setCorner(Position corner) {
        return new Parallelepiped(corner, xSize, ySize, zSize);
    }

    public Parallelepiped setSize(Dimension dimension, int size) {
        switch (dimension) {
            case X:
                return new Parallelepiped(corner, size, ySize, zSize);
            case Y:
                return new Parallelepiped(corner, xSize, size, zSize);
            default:
                return new Parallelepiped(corner, xSize, ySize, size);
        }
    }
}
