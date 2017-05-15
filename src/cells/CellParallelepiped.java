package cells;

import geometry.Parallelepiped;

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
}
