package world;

import cells.WorldCell;
import geometry.Parallelepiped;
import geometry.Position;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

/**
 * A world cell assigned to a parallelepiped
 */
@EqualsAndHashCode
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

    /**
     * Produce the volume occupied by this cellParallelepiped
     * @return volume occupied by this cellParallelepiped
     */
    public int volume() {
        return parallelepiped.volume();
    }
}
