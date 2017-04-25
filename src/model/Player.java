package model;

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
 *      inventory.length == 10
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
    private static Player instance;
    private int health;
    private int strength;
    private int agility;
    private int hunger;
    private Cells[] inventory;
    private int hungerDelay;
    private int selectedInventorySlot;
    private float rotation;

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

        selectedInventorySlot = random.nextInt(10) + 1;
        inventory = new Cells[10];
        inventory[0] = Cells.STATIC;
        inventory[1] = Cells.RANDOM_WALK;
        inventory[2] = Cells.RANDOM_WALK;
        inventory[3] = Cells.RANDOM_WALK;
        inventory[4] = Cells.STATIC;

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
        }
        hungerDelay++;
        }else {
            hungerDelay++;
        }
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

    public Cells getInventoryItem(int index) {
        if (index < inventory.length) {
            return inventory[index];
        } else {
            return null;
        }
    }

    public void setInventoryItem(int index, Cells value) {
        if (index < inventory.length) {
            inventory[index] = value;
        }

        hasValidState();
        setChanged();
        notifyObservers();
    }

    public int getSelectedInventorySlot() {
        return selectedInventorySlot;
    }

    public void setSelectedInventorySlot(Integer selectedInventorySlot) {
        this.selectedInventorySlot = selectedInventorySlot;

        hasValidState();
        setChanged();
        notifyObservers();
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

    public Cells getSelectedItem() {
        return getInventoryItem(getSelectedInventorySlot());
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
        assert   ((validPercentage(health)
                && validPercentage(strength)
                && validPercentage(agility)
                && validPercentage(hunger))
        && (inventory.length == 10)
        && (hungerDelay >= 0)
        && (selectedInventorySlot < inventory.length)
        && (0 <= rotation && rotation <= 2 * Math.PI));
    }
}
