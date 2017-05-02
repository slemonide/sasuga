package geometry;

import exceptions.GeometryMismatch;
import model.Position;

import java.util.HashSet;
import java.util.Set;

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

    public Position getCenter() {
        return center;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int getZSize() {
        return zSize;
    }


    public boolean contains(Position position) {
        return ((xSize - xSize/2 < position.x) && (position.x < xSize/2) &&
                (ySize - ySize/2 < position.y) && (position.y < ySize/2) &&
                (zSize - zSize/2 < position.z) && (position.z < zSize/2));
    }
}
