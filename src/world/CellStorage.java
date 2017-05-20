package world;

import cells.CellParallelepiped;
import cells.WorldCell;
import geometry.Parallelepiped;
import geometry.Position;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * @version     0.1
 * @since       0.1
 *
 * Maps positions to cells
 */
public interface CellStorage {
    /**
     * Adds given cell cellParallelepiped to the storage
     * @param cellParallelepiped cellParallelepiped to be added
     */
    void add(CellParallelepiped cellParallelepiped);

    /**
     * Adds all cellParallelepipeds in the collection to the storage
     * @param cellParallelepipeds cellParallelepipeds to be added
     */
    void addAll(Collection<CellParallelepiped> cellParallelepipeds);

    /**
     * Adds given parallelepiped with assigned worldCell to the storage
     * @param parallelepiped parallelepiped of the worldCell
     * @param worldCell worldCell of the parallelepiped
     */
    void add(Parallelepiped parallelepiped, WorldCell worldCell);

    /**
     * Adds given parallelepiped with assigned worldCell to the storage
     *
     * <p>
     *     Creates a unit parallelepiped from the given position.
     * </p>
     * @param position position of the worldCell
     * @param worldCell worldCell of the position
     */
    void add(Position position, WorldCell worldCell);

    /**
     * Remove the given position from the storage
     * <p>
     *     If this storage does not contain the given position, do nothing.
     * </p>
     * @param position position to be removed
     */
    void remove(Position position);

    /**
     * Remove the given parallelepiped from the storage
     * <p>
     *    If the storage contains this parallelepiped only partially,
     *    remove as much of it as possible.
     *    Otherwise, do nothing.
     * </p>
     * @param parallelepiped position to be removed
     */
    void remove(Parallelepiped parallelepiped);

    /**
     * Remove all positions in the given collection from the storage,
     * if possible
     * @param toRemove positions to be removed
     */
    void removeAll(Collection<Position> toRemove);

    /**
     * Produce true if the given position is in this storage, false otherwise
     * @param position position whose presence in this storage is to be tested
     * @return true if the given position is in this storage, false otherwise
     */
    boolean contains(Position position);

    /**
     * Remove all elements from this CellStorage
     */
    void clear();

    /**
     * Produce true if this CellStorage is empty, false otherwise
     * @return true if this CellStorage is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Produce the number of parallelepipeds in this CellStorage
     * @return number of parallelepipeds in this CellStorage
     */
    int size();

    /**
     * Produce the set of all cell parallelepipeds in the storage
     * @return set of all cell parallelepipeds in the storage
     */
    @NotNull
    Set<CellParallelepiped> cellParallelepipeds();

    /**
     * Produce the set of all positions in the storage
     * @return set of all positions in the storage
     */
    @NotNull
    Set<Position> positions();

    /**
     * Maybe produce the cellParallelepiped containing the given position
     * @param position position that the cellParallelepiped contains
     */
    @NotNull
    Optional<CellParallelepiped> get(Position position);
}
