package cells;

import geometry.Parallelepiped;

/**
 * A world cell assigned to a parallelepiped
 */
public class CellParallelepiped {
    public Parallelepiped parallelepiped;
    public VisibleCell cell;

    public CellParallelepiped(Parallelepiped parallelepiped, VisibleCell visibleCell) {
        this.parallelepiped = parallelepiped;
        this.cell = visibleCell;
    }
}
