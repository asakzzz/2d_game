package game.classes.items;

/**
 *
 * This {@code Item} class is the blueprint for all items in the game Items
 * suchs as health regen, mana regen or offensive items
 */
public class Item {

    protected String name;
    protected String description;
    protected int effect;

    /**
     * This creates the Item object
     *
     * @param name Name of the item
     * @param description Description of the Item
     * @param effect Effect of the item (0 = offensive, 1 = health regen , 2 =
     * mana regen)
     *
     * It also checks if the object's effect is between those ints, if not it
     * returns an error
     */
    public Item(String name, String description, int effect) {
        this.name = name;
        this.description = description;
        if (effect == 0 || effect == 1 || effect == 2) {
            this.effect = effect;
        } else {
            throw new IllegalArgumentException("Invalid effect type: " + effect + ". Expected 0 (Offensive), 1 (Health Regen), or 2 (Mana Regen).");
        }
    }

    /**
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the effect of the item
     */
    public int getEffect() {
        return effect;
    }

    /**
     *
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

}
