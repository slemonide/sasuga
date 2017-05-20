package cells;

import geometry.Parallelepiped;
import geometry.Position;
import org.jetbrains.annotations.NotNull;

/**
 * A world cell assigned to a parallelepiped
 */
public final class CellParallelepiped {
    public final Parallelepiped parallelepiped;
    public final WorldCell cell;

    public CellParallelepiped(@NotNull Parallelepiped parallelepiped,
                              @NotNull WorldCell worldCell) {
        this.parallelepiped = parallelepiped;
        this.cell = worldCell;
    }

    public CellParallelepiped(@NotNull Position position,
                              @NotNull WorldCell worldCell) {
        this.parallelepiped = new Parallelepiped(position);
        this.cell = worldCell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellParallelepiped that = (CellParallelepiped) o;

        return parallelepiped.equals(that.parallelepiped) && cell.equals(that.cell);
    }

    @Override
    public int hashCode() {
        int result = parallelepiped.hashCode();
        result = 31 * result + cell.hashCode();
        return result;
    }
}
