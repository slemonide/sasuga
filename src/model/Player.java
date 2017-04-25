package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
public class Player extends ActiveCell {
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
    private Position cursor;

    /**
     * Create a cell at the given position with randomly chosen stats
     *
     * @param position position of this cell
     */
    private Player(Position position) {
        super(position);
        setName("Player");

        Random random = new Random();

        rotation = 0;

        health = 10 + random.nextInt(91);
        strength = 20 + random.nextInt(81);
        agility = 30 + random.nextInt(71);
        hunger = 40 + random.nextInt(61);

        cursor = new Position();

        inventory = new Inventory(INVENTORY_SIZE);
        inventory.setInventoryItem(1, new InventoryItem("Say Hi",
                () -> System.out.println("Hi")));
        inventory.setInventoryItem(2, new InventoryItem("Cell",
                () ->  World.getInstance().add(new Cell(getCursor()))));
        inventory.setInventoryItem(3, new InventoryItem("Random Walk",
                () ->  World.getInstance().add(new RandomWalkCell(getCursor()))));

        hasValidState();
    }

    /**
     * Singleton pattern
     * @return the player
     */
    public static Player getInstance() {
        if (instance == null){
            instance = new Player(new Position());
        }
        return instance;
    }

    /**
     * Reset the player to the original state
     */
    public void reset() {
        instance = new Player(new Position());
    }


    /**
     * Hunger tick lowers player's hunger by one, and
     * lowers player's stats, if below certain thresholds individual to each stat
     * Counts MIN_HUNGER_DELAY world ticks between each hunger tick
     */
    @Override
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

    @Override
    public Collection<? extends Cell> tickToAdd() {
        return null;
    }

    @Override
    public Collection<? extends Position> tickToRemove() {
        return null;
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

    public void moveCursor(Position delta) {
        cursor = cursor.add(delta);
    }


    /**
     * @param value percentage
     * @return true if 0 <= percentage <= 100, false otherwise
     */
    private boolean validPercentage(int value) {
        return (0 <= value && value <= 100);
    }

    /*
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

    public Position getCursor() {
        return cursor;
    }
}
