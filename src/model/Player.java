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
     * Create a cell at the given position
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
        }else {
            hungerDelay++;
        }
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
        this.health = health;

        setChanged();
        notifyObservers();
    }

    public void setStrength(int strength) {
        this.strength = strength;

        setChanged();
        notifyObservers();
    }

    public void setAgility(int agility) {
        this.agility = agility;

        setChanged();
        notifyObservers();
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;

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

        setChanged();
        notifyObservers();
    }

    public int getSelectedInventorySlot() {
        return selectedInventorySlot;
    }

    public void setSelectedInventorySlot(Integer selectedInventorySlot) {
        this.selectedInventorySlot = selectedInventorySlot;

        setChanged();
        notifyObservers();
    }

    public void rotateCounterClockWise() {
        rotation += ROTATION_SPEED;
        rotation = (float) (rotation % (Math.PI * 2));

        setChanged();
        notifyObservers();
    }

    public void rotateClockWise() {
        rotation -= ROTATION_SPEED;
        rotation = (float) (rotation % (Math.PI * 2));

        setChanged();
        notifyObservers();
    }

    public float getRotation() {
        return rotation;
    }

    public Cells getSelectedItem() {
        return getInventoryItem(getSelectedInventorySlot());
    }
}
