package cells;

import com.jme3.math.ColorRGBA;
import geometry.Position;

import java.util.Observable;

/**
 * @author      Danil Platonov <slemonide@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * Represents a cell with position and name.
 */
public class Cell extends Observable {
    private String name = "Static Cell";
    public Position position;
    protected ColorRGBA color;

    /**
     * Create a cell at the given position
     * @param position position of this cell
     */
    public Cell(Position position) {
        this.position = position;
    }

    /**
     * Return the position of this cell
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColorRGBA getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return (name != null ? name.equals(cell.name) : cell.name == null)
                && (position != null ? position.equals(cell.position) : cell.position == null)
                && (color != null ? color.equals(cell.color) : cell.color == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
