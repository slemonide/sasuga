package world;

import cells.CellParallelepiped;
import geometry.Position;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Maps positions to cells
 */
public class CellStorage {
    private Map<Position, CellParallelepiped> staticCells;
    private Map<Position, CellParallelepiped> activeCells;

    public CellStorage() {
        staticCells = Collections.synchronizedMap(new HashMap<>());
        activeCells = Collections.synchronizedMap(new HashMap<>());
    }
    
    public int size() {
        return (staticCells.size() + activeCells.size());
    }
    
    public boolean isEmpty() {
        return (staticCells.isEmpty() && activeCells.isEmpty());
    }
    
    public boolean containsKey(Object key) {
        return (staticCells.containsKey(key) || activeCells.containsKey(key));
    }
    
    public boolean containsValue(Object value) {
        return (staticCells.containsValue(value) || activeCells.containsValue(value));
    }
    
    public CellParallelepiped get(Object key) {
        return staticCells.getOrDefault(key, activeCells.get(key));
    }
    
    public CellParallelepiped put(Position key, CellParallelepiped value) {
        CellParallelepiped previousValue = get(key);

        // need this to avoid collisions between the maps
        remove(key);

        //if (value instanceof DynamicCell) {
        //    activeCells.put(key, (ActiveCell) value);
        //} else {
            staticCells.put(key, value);
        //}

        return previousValue;
    }

    
    public CellParallelepiped remove(Object key) {
        CellParallelepiped previousValue = get(key);

        activeCells.remove(key);
        staticCells.remove(key);

        return previousValue;
    }

    @NotNull
    public Set<Position> keySet() {
        Set<Position> keySet = new HashSet<>();
        synchronized (staticCells) {
            keySet.addAll(staticCells.keySet());
        }
        synchronized (activeCells) {
            keySet.addAll(activeCells.keySet());
        }

        return keySet;
    }

    @NotNull
    public Collection<CellParallelepiped> values() {
        Collection<CellParallelepiped> valueSet = new HashSet<>();
        synchronized (staticCells) {
            valueSet.addAll(staticCells.values());
        }
        synchronized (activeCells) {
            valueSet.addAll(activeCells.values());
        }

        return valueSet;
    }

    public void removeAll(Set<Position> toRemove) {
        for (Position position : toRemove) {
            remove(position);
        }
    }

    public void addAll(Set<CellParallelepiped> cellParallelepipeds) {
        synchronized (cellParallelepipeds) {
            for (CellParallelepiped cellParallelepiped : cellParallelepipeds) {
                put(cellParallelepiped.parallelepiped.getCorner(), cellParallelepiped);
            }
        }
    }
}
