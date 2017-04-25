package model;

/**
 * An inventory item that has a name and an assigned action
 */
public class InventoryItem {
    private String name;
    private F action;

    /**
     * Create a new InventoryItem with the given name and an assigned action
     */
    public interface F { void f(); }
    public InventoryItem(String name, F action) {
        this.name = name;
        this.action = action;
    }

    /**
     * @return name of this inventory item
     */
    public String getName() {
        return name;
    }

    /**
     * Execute the assigned action
     */
    public void use() {
        if (action != null) {
            action.f();
        }
    }
}
