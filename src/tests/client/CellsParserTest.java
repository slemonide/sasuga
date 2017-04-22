package tests.client;

import client.parsers.CellsParser;
import org.junit.Test;
import server.model.Cell;
import server.model.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 */
public class CellsParserTest {
    CellsParser testParser;

    @Test
    public void testEmptyMessage() {
        testParser = new CellsParser("[]");
        assertTrue(testParser.getCells().isEmpty());
    }

    @Test
    public void testOneCell() throws FileNotFoundException {
        File testFile = new File("src/tests/client/data/oneCell.json");
        Scanner testData = new Scanner(testFile);
        testData.useDelimiter("$^");

        String testString = testData.next();

        testParser = new CellsParser(testString);

        Set<Cell> cells = testParser.getCells();
        assertEquals(1, cells.size());
        assertTrue(cells.contains(new Cell(new Position(1, 2, 3))));
    }

    @Test
    public void testSeveralCells() throws FileNotFoundException {
        File testFile = new File("src/tests/client/data/severalCells.json");
        Scanner testData = new Scanner(testFile);
        testData.useDelimiter("$^");

        String testString = testData.next();

        testParser = new CellsParser(testString);

        Set<Cell> cells = testParser.getCells();
        assertEquals(6, cells.size());
        assertTrue(cells.contains(new Cell(new Position(1, 2, 3))));
        assertTrue(cells.contains(new Cell(new Position(-12, 2))));
        assertTrue(cells.contains(new Cell(new Position(1, 0, 3))));
        assertTrue(cells.contains(new Cell(new Position(-1, -2, 3, 5, 2))));
        assertTrue(cells.contains(new Cell(new Position(1))));
        assertTrue(cells.contains(new Cell(new Position())));
    }
}
