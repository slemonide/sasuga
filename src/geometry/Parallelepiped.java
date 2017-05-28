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

    /**
     * Creates a parallelepiped with the given dimensions centered at the given position
     * @param corner minimal position of the parallelepiped
     * @param sides contains the dimensions of the sides
     * @throws IllegalArgumentException if any of the dimensions are not positive
     */
    public Parallelepiped(Position corner, @NotNull Position sides) {
        if (sides.x <= 0)
            throw new IllegalArgumentException("x dimension must be positive");
        if (sides.y <= 0)
            throw new IllegalArgumentException("y dimension must be positive");
        if (sides.z <= 0)
            throw new IllegalArgumentException("z dimension must be positive");

        this.corner = corner;
        this.xSize = sides.x;
        this.ySize = sides.y;
        this.zSize = sides.z;
    }

    @Contract(pure = true)
    @NotNull
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

    @Contract(pure = true)
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

    @Contract(pure = true)
    @NotNull
    public Parallelepiped setCorner(@NotNull Position corner) {
        return new Parallelepiped(corner, xSize, ySize, zSize);
    }

    @Contract(pure = true)
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
     * Produce the volume of this parallelepiped
     *
     * <p>
     *     Volume is equal to xSize * ySize * zSize
     * </p>
     * @return volume of this parallelepiped
     */
    @Contract(pure = true)
    public int volume() {
        int volumeSoFar = 1;

        for (Axis axis : Axis.values()) {
            volumeSoFar *= getSize(axis);
        }

        return volumeSoFar;
    }

    @Contract(pure = true)
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
    @Contract(pure = true)
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
    @Contract(pure = true)
    @NotNull
    Collection<? extends Parallelepiped> getInterlockingNeighbours(@NotNull ParallelepipedSpace space,
                                                                   @NotNull Axis axis) {
        Set<Parallelepiped> neighbours = new HashSet<>();

        Position unitVector = axis.getUnitVector();

        space.get(this.getCorner().add(unitVector.multiply(this.getSize(axis)))).ifPresent(neighbours::add);
        space.get(this.getCorner().add(unitVector.inverse())).ifPresent(neighbours::add);

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
    @Contract(pure = true)
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
     * If index is in [1,volume()], then produce a unique position inside this parallelepiped,
     * otherwise throw IllegalArgumentException
     * @param index any positive number between 1 and volume() (inclusive)
     * @return position in this parallelepiped
     * @throws IllegalArgumentException if index is less then 1 or greater then volume()
     */
    @Contract(pure = true)
    @NotNull
    public Position positionFromIndex(int index) throws IllegalArgumentException {
        if (index < 1 || index > volume()) {
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
    @Contract(pure = true)
    @NotNull
    public Stream<Position> positions() {
        return Stream.iterate(1, i -> i+1).limit(volume()).map(this::positionFromIndex);
    }

    /**
     * Produce the (approximate) center of this parallelepiped
     * @return the (approximate) center of this parallelepiped
     */
    @Contract(pure = true)
    @NotNull
    public Position center() {
        return new Position(
                corner.x + (xSize - 1) / 2,
                corner.y + (ySize - 1) / 2,
                corner.z + (zSize - 1) / 2);
    }

    /**
     * Computes the weighted average center position between this and that
     * @param that the other parallelepiped
     * @return the weighted average center position
     */
    @Contract(pure = true)
    @NotNull
    public Position averageCenterPosition(Parallelepiped that) {

        return (this.center().multiply(this.volume()))
                .add(that.center().multiply(that.volume()))
                .divide(this.volume() + that.volume());
    }

    /**
     * Produces all sides as a vector
     * @return all sides as a vector
     */
    @Contract(pure = true)
    @NotNull
    public Position getSides() {
        return new Position(xSize, ySize, zSize);
    }

    /**
     * Sets the sides of this parallelepiped
     * @param sides contains the sides of the parallelepiped
     * @return parallelepiped with the given sides
     */
    @Contract(pure = true)
    @NotNull
    public Parallelepiped setSides(@NotNull Position sides) {
        return new Parallelepiped(corner, sides);
    }
}

