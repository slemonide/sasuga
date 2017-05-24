package world;

import cells.WorldCell;
import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Set-based implementation of CellStorage
 */
public class HashMapCellStorage implements CellStorage {
    private Map<WorldCell, ParallelepipedSpace> storage;

    /**
     * Create a new empty cell storage
     */
    HashMapCellStorage() {
        storage = new HashMap<>();
    }

    /**
     * Create a new cell storage containing all the CellParallelepiped's
     * from the given collection
     *
     * @param cellParallelepipeds the collection of elements to initially contain
     * @throws NullPointerException if the specified collection is null
     */
    HashMapCellStorage(Collection<CellParallelepiped> cellParallelepipeds) throws NullPointerException {
        storage = new HashMap<>();
        addAll(cellParallelepipeds);
    }

    /**
     * Adds given cell cellParallelepiped to the storage
     *
     * @param cellParallelepiped cellParallelepiped to be added
     */
    @Override
    public void add(CellParallelepiped cellParallelepiped) {
        add(cellParallelepiped.parallelepiped, cellParallelepiped.cell);
    }

    private void removeOverlappingRegions(ParallelepipedSpace parallelepipedSpace) {
        for (ParallelepipedSpace otherParallelepipedSpace : storage.values()) {
            parallelepipedSpace.removeAll(otherParallelepipedSpace);
        }
    }

    /**
     * Adds all cellParallelepipeds in the collection to the storage
     *
     * @param cellParallelepipeds cellParallelepipeds to be added
     */
    @Override
    public void addAll(Collection<CellParallelepiped> cellParallelepipeds) {
        cellParallelepipeds.forEach(this::add);
    }

    /**
     * Adds given parallelepiped with assigned worldCell to the storage
     *
     * @param parallelepiped parallelepiped of the worldCell
     * @param material      worldCell of the parallelepiped
     */
    @Override
    public void add(Parallelepiped parallelepiped, WorldCell material) {
        ParallelepipedSpace parallelepipedSpace = new ParallelepipedSpace();
        parallelepipedSpace.add(parallelepiped);
        removeOverlappingRegions(parallelepipedSpace);

        if (storage.containsKey(material)) {
            storage.get(material).addAll(parallelepipedSpace);
        } else {
            storage.put(material, parallelepipedSpace);
        }
    }

    /**
     * Adds given parallelepiped with assigned worldCell to the storage
     * <p>
     * Creates a unit parallelepiped from the given position.
     * </p>
     *
     * @param position  position of the worldCell
     * @param worldCell worldCell of the position
     */
    @Override
    public void add(Position position, WorldCell worldCell) {
        if (contains(position)) {
            return;
        }

        Parallelepiped parallelepiped = new Parallelepiped(position);

        add(parallelepiped, worldCell);
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
        storage.forEach((wc, ps) -> ps.remove(position));
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
        storage.forEach((wc, ps) -> ps.remove(parallelepiped));
    }

    /**
     * Remove all positions in the given collection from the storage,
     * if possible
     *
     * @param toRemove positions to be removed
     */
    @Override
    public void removeAll(Collection<Position> toRemove) {
        toRemove.forEach(this::remove);
    }

    /**
     * Produce true if the given position is in this storage, false otherwise
     *
     * @param position position whose presence in this storage is to be tested
     * @return true if the given position is in this storage, false otherwise
     */
    @Override
    public boolean contains(Position position) {
        for (ParallelepipedSpace parallelepipedSpace : storage.values()) {
            if (parallelepipedSpace.contains(position)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Remove all elements from this CellStorage
     */
    @Override
    public void clear() {
        storage.clear();
    }

    /**
     * Produce true if this CellStorage is empty, false otherwise
     *
     * @return true if this CellStorage is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        for (ParallelepipedSpace parallelepipedSpace : storage.values()) {
            if (!parallelepipedSpace.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Produce the number of parallelepipeds in this CellStorage
     *
     * @return number of parallelepipeds in this CellStorage
     */
    @Override
    public int size() {
        int sizeSoFar = 0;

        for (ParallelepipedSpace parallelepipedSpace : storage.values()) {
            sizeSoFar += parallelepipedSpace.size();
        }

        return sizeSoFar;
    }

    /**
     * Produce the set of all cell parallelepipeds in the storage
     *
     * @return set of all cell parallelepipeds in the storage
     */
    @Override
    public @NotNull Set<CellParallelepiped> cellParallelepipeds() {
        Set<CellParallelepiped> cellParallelepipeds = new HashSet<>();

        for (WorldCell worldCell : storage.keySet()) {
            for (Parallelepiped parallelepiped : storage.get(worldCell)) {
                cellParallelepipeds.add(new CellParallelepiped(parallelepiped, worldCell));
            }
        }

        return cellParallelepipeds;
    }

    /**
     * Produce the set of all positions in the storage
     *
     * @return set of all positions in the storage
     */
    @Override
    public @NotNull Set<Position> positions() {
        Set<Position> positions = new HashSet<>();

        for (ParallelepipedSpace parallelepipedSpace : storage.values()) {
            positions.addAll(parallelepipedSpace.positions().collect(Collectors.toSet()));
        }

        return positions;
    }

    /**
     * Maybe produce the cellParallelepiped containing the given position
     *
     * @param position position that the cellParallelepiped contains
     */
    @Override
    public @NotNull Optional<CellParallelepiped> get(Position position) {
        for (WorldCell worldCell : storage.keySet()) {
            ParallelepipedSpace parallelepipedSpace = storage.get(worldCell);
            if (parallelepipedSpace.contains(position)) {
                CellParallelepiped cellParallelepiped =
                        new CellParallelepiped(parallelepipedSpace.get(position), worldCell);

                return Optional.of(cellParallelepiped);
            }
        }

        return Optional.empty();
    }
}
