package ui;

import com.jme3.math.Vector3f;
import model.Position;

import static ui.Environment.SCALE;

/**
 * Contains helper methods to transform coordinates from World coordinates to VisualGUI coordinates
 */
public class Coordinates {
    public static Vector3f positionToVector(Position position) {
        return new Vector3f(
                position.x * SCALE,
                position.y * SCALE,
                position.z * SCALE);
    }
}
