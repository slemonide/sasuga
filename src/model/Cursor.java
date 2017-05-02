package model;

import java.util.Collection;

/**
 * @author Ray Zhang <raythemathguy@gmail.com>
 * @version 0.1
 * @since 0.1
 *
 * A cell which represents the player's cursor.
 */
public class Cursor extends ActiveCell {

    private static final double MAX_DISTANCE = 20;



    public Cursor(Position position){
        super(position);
    }

    public void tick(){
        this.position = updateCursor();
    }

    private Position updateCursor(){
        Position tempCheck = new Position(0,0,0);
        double distance = 0;

        //check maintains that a block at max range contains a point within MAX_DISTANCE from camera on gaze ray
        while (distance < MAX_DISTANCE){




            tempCheck.subtract(position);

            //Collection<Integer> distanceCoordinates = tempCheck.getActiveComponents().values();

            // list fold all coordinate differences
            //distance = distanceCoordinates.stream()
            //        .reduce(0, (a, b) -> Math.abs(a) + b);   // currently using l1 norm (l2 norm also possible)
        }

        return tempCheck;  //returns hollow block at max distance if no collision within MAX_DISTANCE
    }






    public Collection<? extends Cell> tickToAdd(){
        return null;
    }

    public Collection<? extends Position> tickToRemove(){
        return null;
    }
}
