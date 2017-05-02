package model;

import model.Player;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Player class
 */
public class PlayerTest {
    @Before
    public void runBefore() {
        Player.getInstance().reset();
    }

    @Test
    public void testConstructor() {
        // stub
    }
}
