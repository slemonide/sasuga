package world;

import cells.CellParallelepiped;
import cells.WorldCell;
import geometry.Parallelepiped;
import geometry.Position;

import java.util.Collection;
import java.util.Set;

/**
 * Set-based implementation of CellStorage
 */
public class SetCellStorage implements CellStorage {
    
    public SetCellStorage() {

    }

    /**
     * Adds given cell cellParallelepiped to the storage
     *
     * @param cellParallelepiped cellParallelepiped to be added
     */
    @Override
    public void add(CellParallelepiped cellParallelepiped) {

    }

    /**
     * Adds all cellParallelepipeds in the collection to the storage
     *
     * @param cellParallelepipeds cellParallelepipeds to be added
     */
    @Override
    public void addAll(Collection<CellParallelepiped> cellParallelepipeds) {

    }

    /**
     * Adds given parallelepiped with assigned worldCell to the storage
     *
     * @param parallelepiped parallelepiped of the worldCell
     * @param worldCell      worldCell of the parallelepiped
     */
    @Override
    public void add(Parallelepiped parallelepiped, WorldCell worldCell) {

    }

    /**
     * Adds given parallelepiped with assigned worldCell to the storage
     * <p>
     * <p>
     * Creates a unit parallelepiped from the given position.
     * </p>
     *
     * @param position  position of the worldCell
     * @param worldCell worldCell of the position
     */
    @Override
    public void add(Position position, WorldCell worldCell) {

    }

    /**
     * Remove the given position from the storage
     * <p>
     * If this storage does not contain the given position, do nothing.
     * </p>
     *
     * @param position position to be removed
     */
    @Override
    public void remove(Position position) {

    }

    /**
     * Remove the given parallelepiped from the storage
     * <p>
     * If the storage contains this parallelepiped only partially,
     * remove as much of it as possible.
     * Otherwise, do nothing.
     * </p>
     *
     * @param parallelepiped position to be removed
     */
    @Override
    public void remove(Parallelepiped parallelepiped) {

    }

    /**
     * Remove all positions in the given collection from the storage,
     * if possible
     *
     * @param toRemove positions to be removed
     */
    @Override
    public void removeAll(Collection<Position> toRemove) {

    }

    /**
     * Produce the set of all cell parallelepipeds in the storage
     *
     * @return set of all cell parallelepipeds in the storage
     */
    @Override
    public Set<CellParallelepiped> cellParallelepipeds() {
        return null;
    }

    /**
     * Produce the set of all positions in the storage
     *
     * @return set of all positions in the storage
     */
    @Override
    public Set<Position> positions() {
        return null;
    }

    /**
     * Produce true if the given position is in this storage, false otherwise
     *
     * @param position position whose presence in this storage is to be tested
     * @return true if the given position is in this storage, false otherwise
     */
    @Override
    public boolean contains(Position position) {
        return false;
    }
}
