package world;

import cells.WorldCell;
import geometry.Parallelepiped;
import geometry.ParallelepipedSpace;
import geometry.Position;
import org.jetbrains.annotations.NotNull;
import util.CollectionObserver;
import util.Difference;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * HashMap-based implementation of CellStorage
 */
public class HashMapCellStorage implements CellStorage {
    private Map<WorldCell, ParallelepipedSpace> storage;

    /**
     * Create a new empty cell storage
     */
    public HashMapCellStorage() {
        storage = new HashMap<>();
    }

    /**
     * Create a new cell storage containing all the CellParallelepiped's
     * from the given collection
     *
     * @param cellParallelepipeds the collection of elements to initially contain
     * @throws NullPointerException if the specified collection is null
     */
    public HashMapCellStorage(@NotNull Collection<CellParallelepiped> cellParallelepipeds) throws NullPointerException {
        storage = new HashMap<>();
        addAll(cellParallelepipeds);
    }

    /**
     * Construct a new cell storage with the same contents as the given storage
     * @param otherStorage storage from which to copy contents
     */
    public HashMapCellStorage(@NotNull CellStorage otherStorage) {
        storage = new HashMap<>();
        addAll(otherStorage.cellParallelepipeds());
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
        storage.values().forEach(parallelepipedSpace::removeAll);
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
        return storage.values().parallelStream()
                .anyMatch(parallelepipeds -> parallelepipeds.contains(position));
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
        return storage.values().parallelStream()
                .allMatch(ParallelepipedSpace::isEmpty);
    }

    /**
     * Produce the number of parallelepipeds in this CellStorage
     *
     * @return number of parallelepipeds in this CellStorage
     */
    @Override
    public int size() {
        return storage.values().parallelStream().mapToInt(ParallelepipedSpace::size).sum();
    }

    /**
     * Produce the stream of all cell parallelepipeds in the storage
     *
     * @return stream of all cell parallelepipeds in the storage
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

            Optional<Parallelepiped> optionalParallelepiped = parallelepipedSpace.get(position);

            if (optionalParallelepiped.isPresent()) {
                CellParallelepiped cellParallelepiped =
                        new CellParallelepiped(optionalParallelepiped.get(), worldCell);

                return Optional.of(cellParallelepiped);
            }
        }

        return Optional.empty();
    }

    /**
     * Helps analyzing changes in the storage
     *
     * @return difference between current storage and its last seen version
     */
    @Override
    public @NotNull CollectionObserver<CellParallelepiped> registerCollectionObserver() {
        return new CellStorageObserver(this);
    }

    private class CellStorageObserver implements CollectionObserver<CellParallelepiped> {
        private HashMapCellStorage current;
        private HashMapCellStorage old;

        CellStorageObserver(HashMapCellStorage current) {
            this.current = current;
            this.old = new HashMapCellStorage(current);
        }

        @Override
        public Difference<CellParallelepiped> getDifference() {
            Set<CellParallelepiped> added = new HashSet<>();
            Set<CellParallelepiped> removed = new HashSet<>();

            added.addAll(current.cellParallelepipeds());
            added.removeAll(old.cellParallelepipeds());

            removed.addAll(old.cellParallelepipeds());
            removed.removeAll(current.cellParallelepipeds());

            old.clear();
            old.addAll(current.cellParallelepipeds());

            return new Difference<>(added, removed, new HashMap<>());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashMapCellStorage that = (HashMapCellStorage) o;

        return storage.equals(that.storage);
    }

    @Override
    public int hashCode() {
        return storage.hashCode();
    }
}
