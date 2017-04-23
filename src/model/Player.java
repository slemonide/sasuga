package model;

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
    private static final int MIN_HUNGER_DELAY = 1; // in ticks
    private static Player instance;
    private int health;
    private int strength;
    private int agility;
    private int hunger;
    private Cell[] inventory;
    private int hungerDelay;

    /**
     * Create a cell at the given position
     *
     * @param position position of this cell
     */
    private Player(Position position) {
        super(position);

        health = 10;
        strength = 20;
        agility = 30;
        hunger = 40;

        inventory = new Cell[10];
    }

    @Override
    public void tick() {
        if (hungerDelay > MIN_HUNGER_DELAY) {
            setHunger(getHunger() - 1);
            hungerDelay = 0;
        }
        hungerDelay++;
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

    public Cell getInventoryItem(int index) {
        if (index < inventory.length) {
            return inventory[index];
        } else {
            return null;
        }
    }

    public void setInventoryItem(int index, Cell value) {
        if (index < inventory.length) {
            inventory[index] = value;
        }

        setChanged();
        notifyObservers();
    }
}
