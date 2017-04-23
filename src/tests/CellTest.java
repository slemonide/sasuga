package tests;

import org.junit.Before;
import org.junit.Test;
import model.Cell;
import model.Position;

import static org.junit.Assert.*;

/**
 * Unit test for the Cell class
 */
public class CellTest {
    Cell testCell;

    @Before
    public void runBefore() {
        testCell = new Cell(new Position(1, 2));
    }

    @Test
    public void testConstructor() {
        assertEquals(new Position(1, 2), testCell.getPosition());
    }

}
