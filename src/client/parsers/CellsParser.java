package client.parsers;

import org.json.JSONArray;
import org.json.JSONObject;
import server.model.Cell;
import server.model.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Parses the cells json message sent from the server
 */
public class CellsParser {
    private Set<Cell> cells;

    public CellsParser(String jsonCells) {
        cells = new HashSet<>();

        System.out.println(jsonCells);
        JSONArray cellsArray = new JSONArray(jsonCells);

        for (int i = 0; i < cellsArray.length(); i++) {
            parseCell(cellsArray.getJSONObject(i));
        }

    }

    private void parseCell(JSONObject cellJsonObject) {
        Vector vector = getVector(cellJsonObject.getJSONArray("Vector"));

        cells.add(new Cell(vector));
    }

    private Vector getVector(JSONArray vectorArray) {
        List<Integer> coordinates = new ArrayList<>();

        for (int i = 0; i < vectorArray.length(); i++) {
            coordinates.add(vectorArray.getInt(i));
        }

        return new Vector(coordinates);
    }

    public Set<Cell> getCells() {
        return cells;
    }
}
