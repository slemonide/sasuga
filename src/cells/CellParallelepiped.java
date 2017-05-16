package cells;

import geometry.Parallelepiped;
import geometry.Position;

/**
 * A world cell assigned to a parallelepiped
 */
public class CellParallelepiped {
    public Parallelepiped parallelepiped;
    public WorldCell cell;

    public CellParallelepiped(Parallelepiped parallelepiped, WorldCell worldCell) {
        this.parallelepiped = parallelepiped;
        this.cell = worldCell;
    }

    public CellParallelepiped(Position position, WorldCell worldCell) {
        this.parallelepiped = new Parallelepiped(position);
        this.cell = worldCell;
    }
}
