package geometry;

import com.jme3.math.Vector3f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Represents a right-angled parallelepiped with integer-valued side lengths
 *
 * invariant
 *     [x/y/z]Size are always positive
 */
public final class Parallelepiped {
    private final Position corner;
    private final int xSize;
    private final int ySize;
    private final int zSize;

    /**
     * Creates a unit parallelepiped cornered at the given position
     * @param corner minimal position of the parallelepiped
     */
    public Parallelepiped(Position corner) {
        this.corner = corner;
        this.xSize = 1;
        this.ySize = 1;
        this.zSize = 1;
    }

    /**
     * Creates a parallelepiped with the given dimensions centered at the given position
     * @param corner minimal position of the parallelepiped
     * @param xSize x-dimension of the parallelepiped
     * @param ySize y-dimension of the parallelepiped
     * @param zSize z-dimension of the parallelepiped
     * @throws IllegalArgumentException if any of the dimensions are not positive
     */
    public Parallelepiped(@NotNull Position corner, int xSize, int ySize, int zSize) throws IllegalArgumentException {
        if (xSize <= 0)
            throw new IllegalArgumentException("xSize must be positive");
        if (ySize <= 0)
            throw new IllegalArgumentException("ySize must be positive");
        if (zSize <= 0)
            throw new IllegalArgumentException("zSize must be positive");

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
    public int getSize(@NotNull Axis axis) {
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
    public boolean contains(@NotNull Position position) {
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

    @NotNull Parallelepiped setCorner(@NotNull Position corner) {
        return new Parallelepiped(corner, xSize, ySize, zSize);
    }

    @NotNull Parallelepiped setSize(@NotNull Axis axis, int size) {
        switch (axis) {
            case X:
                return new Parallelepiped(corner, size, ySize, zSize);
            case Y:
                return new Parallelepiped(corner, xSize, size, zSize);
            default:
                return new Parallelepiped(corner, xSize, ySize, size);
        }
    }

    /**
     * Produce the getVolume of this parallelepiped
     *
     * <p>
     *     Volume is equal to xSize * ySize * zSize
     * </p>
     * @return getVolume of this parallelepiped
     */
    public long getVolume() {
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
    boolean intersects(@NotNull Parallelepiped otherParallelepiped) {
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
     * @return collection of found neighbours
     */
    Collection<? extends Parallelepiped> getInterlockingNeighbours(@NotNull ParallelepipedSpace space,
                                                                   @NotNull Axis axis) {
        Set<Parallelepiped> neighbours = new HashSet<>();

        Position unitVector = axis.getUnitVector();

        neighbours.add(space.get(this.getCorner().add(unitVector.scale(this.getSize(axis)))));

        neighbours.add(space.get(this.getCorner().add(unitVector.inverse())));

        neighbours.remove(null);
        return neighbours;
    }

    /**
     * Checks if given parallelepipeds can be combined into one parallelepiped
     * based on whether their sizes match and whether their positions coincide
     * @param axis axis that passes through both parallelepipeds
     * @param parallelepiped parallelepiped to check the fitness with
     * @return true if the sizes of parallelepipeds match, false otherwise
     */
    boolean fits(@NotNull Axis axis,
                 @NotNull Parallelepiped parallelepiped) {

        for (Axis complementAxis : axis.getComplements()) {
            if (this.getSize(complementAxis) != parallelepiped.getSize(complementAxis)
                    || this.getCorner().get(complementAxis) != parallelepiped.getCorner().get(complementAxis)) {
                return false;
            }
        }

        return true;
    }

    /**
     * If index is in [1,getVolume()], then produce a unique position inside this parallelepiped,
     * otherwise throw IllegalArgumentException
     * @param index any positive number between 1 and getVolume() (inclusive)
     * @return position in this parallelepiped
     * @throws IllegalArgumentException if index is less then 1 or greater then getVolume()
     */
    @NotNull
    public Position positionFromIndex(int index) throws IllegalArgumentException {
        if (index < 1 || index > getVolume()) {
            throw new IllegalArgumentException();
        }

        int x = index % xSize;
        assert x < xSize;
        int y = (index / xSize) % ySize;
        assert y < ySize;
        int z = (index / (xSize * ySize)) % zSize;
        assert z < zSize;

        Position position = corner.add(x, y, z);

        assert this.contains(position);

        return position;
    }

    /**
     * Produce the set of all positions in the parallelepiped
     *
     * @return set of all positions in the parallelepiped
     */
    public Stream<Position> positions() {
        return Stream.iterate(1, i -> i+1).limit(getVolume()).map(this::positionFromIndex);
    }
}
