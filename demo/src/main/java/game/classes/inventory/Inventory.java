package game.classes.inventory;

import java.util.ArrayList;

import game.classes.items.*;

/**
 * 
 * This will serve as the inventory of the entity
 */

public class Inventory {

    private ArrayList<Item> inventory;
    int max_size = 8;

    /**
     * This create the object {@code Inventory}.
     * No params on this constructor, we need to initiate a blank one for later use
     */

    public Inventory() {
        this.inventory = new ArrayList<Item>();
    }

    /**
     * 
     * @return the {@code inventory} of the entity
     */

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Restricts the size of the inventory to 8, for gameplay balancing purposes
     * @param item The item we want to add to the inventory of the entity
     * @param max_size The maximum size the inventory should stop to
     * 
     * If there is enough room in the inventory for the item, it gets added, otherwise the method stops
     */

    public void addItemInventory(Item item) {
        if (this.inventory.size() == max_size) {
            return;
        }
        this.inventory.add(item);
    }

    /**
     * Allows to remove an item from the inventory, useful upon using or discarding the item
     * @param item The item we want to remove from the inventory of the user
     */

    public void removeItemInventory(Item item) {
        this.inventory.remove(item);
    }
    
}
