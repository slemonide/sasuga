package geometry;

import com.jme3.math.Vector3f;
import model.Position;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

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
    public int getSize(Axis axis) {
        switch (axis) {
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

    @Contract(pure = true)
    @Override
    public int hashCode() {
        int result = corner.hashCode();
        result = 31 * result + xSize;
        result = 31 * result + ySize;
        result = 31 * result + zSize;
        return result;
    }

    @NotNull
    public Parallelepiped setCorner(Position corner) {
        return new Parallelepiped(corner, xSize, ySize, zSize);
    }

    @NotNull
    public Parallelepiped setSize(Axis axis, int size) {
        switch (axis) {
            case X:
                return new Parallelepiped(corner, size, ySize, zSize);
            case Y:
                return new Parallelepiped(corner, xSize, size, zSize);
            default:
                return new Parallelepiped(corner, xSize, ySize, size);
        }
    }

    public int getVolume() {
        int volumeSoFar = 1;

        for (Axis axis : Axis.values()) {
            volumeSoFar *= getSize(axis);
        }

        return volumeSoFar;
    }

    @NotNull
    public Vector3f getWorldVector3f() {
        return new Vector3f(
                corner.x + (xSize - 1) / 2f,
                corner.y + (ySize - 1) / 2f,
                corner.z + (zSize - 1) / 2f);
    }

    /**
     * @return true if this parallelepiped intersects with the given parallelepiped,
     * false otherwise
     */
    public boolean intersects(Parallelepiped otherParallelepiped) {
        for (Axis axis : Axis.values()) {
            IntegerInterval intervalA = new IntegerInterval(
                    this.getCorner().get(axis),
                    this.getSize(axis));
            IntegerInterval intervalB = new IntegerInterval(
                    otherParallelepiped.getCorner().get(axis),
                    otherParallelepiped.getSize(axis));

            if (!intervalA.intersects(intervalB)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Produce the neighbours whose corners lie on the given axis that passes through the center of this parallelepiped
     *
     * @param space space that this parallelepiped lives in
     * @param axis axis on which to check for neighbours
     * @return list of found neighbours
     */
    public Iterable<? extends Parallelepiped> getInterlockingNeighbours(ParallelepipedSpace space, Axis axis) {
        Set<Parallelepiped> neighbours = new HashSet<>();

        Position unitVector = axis.getUnitVector();

        neighbours.add(space.get(this.getCorner()
                .add(unitVector.scale(this.getSize(axis)))));

        neighbours.add(space.get(this.getCorner()
                .add(unitVector.inverse())));

        neighbours.remove(null);
        return neighbours;
    }
}
