package cells;

import com.jme3.math.Vector3f;
import geometry.Position;
import inventory.Inventory;
import world.*;

import java.util.Collection;
import java.util.Observable;
import java.util.Random;

/**
 *
 * @author      Danil Platonov <slemonide@gmail.com>, jacketsj <jacketsj@gmail.com>
 * @version     0.1
 * @since       0.1
 *
 * A player is a cell with health, strength, agility, hunger (in percents)
 * and a 10-item inventory of cells (or nulls)
 *
 * invariant
 *      0 <= health, strength, agility, hunger <= 100
 *      inventory.length == INVENTORY_SIZE
 *      hungerDelay >= 0
 *      selectedInventorySlot < inventory.length
 *      0 <= rotation <= 2 * Math.PI
 */
public class Player extends Observable {
    private static final int MIN_HUNGER_DELAY = World.TICKS_PER_SECOND; // in ticks
    private static final int STRENGTH_REDUCTION_THRESHOLD = 30;
    private static final int AGILITY_REDUCTION_THRESHOLD = 20;
    private static final int HEALTH_REDUCTION_THRESHOLD = 5;
    private static final float ROTATION_SPEED = 0.01f;
    public static final int INVENTORY_SIZE = 10;
    private static Player instance;
    private int health;
    private int strength;
    private int agility;
    private int hunger;
    private Inventory inventory;
    private int hungerDelay;
    private float rotation;
    private Vector3f selectedBlock;
    private Vector3f selectedBlockFace;
    private Position position;

    /**
     * Create a cell at the given position with randomly chosen stats
     *
     * @param position position of this cell
     */
    public Player(Position position) {
        selectedBlock = position.getUIVector();
        selectedBlockFace = position.getUIVector();

        Random random = new Random();

        rotation = 0;

        health = 10 + random.nextInt(91);
        strength = 20 + random.nextInt(81);
        agility = 30 + random.nextInt(71);
        hunger = 40 + random.nextInt(61);

        inventory = new Inventory(this, INVENTORY_SIZE);

        for (StaticCell staticCell : StaticCell.values()) {
            inventory.add(staticCell);
        }

        hasValidState();
    }

    /**
     * Singleton pattern
     * @return the player
     */
    public static Player getInstance() {
        if (instance == null){
            instance = new Player(new Position(0,0,0));
        }
        return instance;
    }

    /**
     * Reset the player to the original state
     */
    public void reset() {
        instance = new Player(new Position(0,0,0));
    }


    /**
     * Hunger tick lowers player's hunger by one, and
     * lowers player's stats, if below certain thresholds individual to each stat
     * Counts MIN_HUNGER_DELAY world ticks between each hunger tick
     */
    public void tick() {
        if (hungerDelay > MIN_HUNGER_DELAY) {
            int hunger = getHunger();
            setHunger(Math.max(hunger - 1, 0));

            //Reduce strength
            if (hunger < STRENGTH_REDUCTION_THRESHOLD) {
                setStrength(Math.max(getStrength() - (int) (3.0 / ((hunger + 1) * 0.1)), 0));

                //Reduce agility
                if (hunger < AGILITY_REDUCTION_THRESHOLD) {
                    setAgility(Math.max(getAgility() - (int) (1.0 / ((hunger + 1) * 0.1)), 0));

                    //Reduce health
                    if (hunger < HEALTH_REDUCTION_THRESHOLD) {
                        setHealth(Math.max(getHealth() - (int) (1.0 / ((hunger + 1) * 0.1)), 0));
                    }
                }
            }
            hungerDelay = 0;
        }else{
            hungerDelay++;
        }

        hasValidState();
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHealth(int health) {
        if (!validPercentage(health)) {
            return;
        }
        this.health = health;

        hasValidState();
        setChanged();
        notifyObservers();
    }

    public void setStrength(int strength) {
        if (!validPercentage(health)) {
            return;
        }
        this.strength = strength;

        hasValidState();
        setChanged();
        notifyObservers();
    }

    public void setAgility(int agility) {
        if (!validPercentage(health)) {
            return;
        }
        this.agility = agility;

        hasValidState();
        setChanged();
        notifyObservers();
    }

    public void setHunger(int hunger) {
        if (!validPercentage(health)) {
            return;
        }
        this.hunger = hunger;

        hasValidState();
        setChanged();
        notifyObservers();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void rotateCounterClockWise() {
        rotation += ROTATION_SPEED;
        rotation = (float) (rotation % (Math.PI * 2));

        hasValidState();
        setChanged();
        notifyObservers();
    }

    public void rotateClockWise() {
        rotation -= ROTATION_SPEED;
        rotation = (float) (rotation % (Math.PI * 2));

        hasValidState();
        setChanged();
        notifyObservers();
    }

    public float getRotation() {
        return rotation;
    }

    /**
     * @param value percentage
     * @return true if 0 <= percentage <= 100, false otherwise
     */
    private boolean validPercentage(int value) {
        return (0 <= value && value <= 100);
    }

    /**
     * Check invariant.
    */
    private void hasValidState() {
        assert validPercentage(health);
        assert validPercentage(strength);
        assert validPercentage(agility);
        assert validPercentage(hunger);
        //assert inventory.length == INVENTORY_SIZE;
        assert hungerDelay >= 0;
        //assert selectedInventorySlot < inventory.length;
        assert (0 <= rotation && rotation <= 2 * Math.PI);
    }

    public Position getSelectedBlock() {
        return Position.fromUIVector(selectedBlock);
    }
    public Position getSelectedBlockFace() {
        return Position.fromUIVector(selectedBlockFace);
    }

    public void setSelectedBlock(Vector3f selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    public void setSelectedBlockFace(Vector3f selectedBlockFace) {
        this.selectedBlockFace = selectedBlockFace;
    }

    public Position getPosition() {
        return position;
    }
}
