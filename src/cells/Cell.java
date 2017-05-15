package cells;

import geometry.Parallelepiped;

/**
 * A world cell assigned to a parallelepiped
 */
public class Cell {
    public Parallelepiped parallelepiped;
    public WorldCell cell;

    public Cell(Parallelepiped parallelepiped, WorldCell worldCell) {
        this.parallelepiped = parallelepiped;
        this.cell = worldCell;
    }
}
