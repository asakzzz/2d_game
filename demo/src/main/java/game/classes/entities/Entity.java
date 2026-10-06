package game.classes.entities;

import game.classes.inventory.Inventory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * This is the blueprint for all entities in the game. Entities such as players
 * and monsters will extend from this class.
 */
public abstract class Entity {

    protected int Hp;
    protected int Def;
    protected int Mana;
    protected Inventory inventory;
    protected Image image;
    ImageView imageView;
    protected double x_pos;
    protected double y_pos;
    protected boolean facingLeft;
    protected boolean facingRight;

    /**
     * Constructs a new {@code Entity} with three parameters {@code hp, def} and
     * {@code mana}.
     *
     * @param hp Health points of the entity, meant to determine if the entity
     * is alive or not.
     * @param def Defense points of the entity, meant to reduce incoming damage.
     * @param mana Mana points of the entity, meant to allow the entity to cast
     * spells or use items, decrease upon use. A new {@code inventory} is
     * initialized upon creating an entity
     * @param image The sprite of the entity, used to render it in the game
     */
    public Entity(int hp, int def, int mana, Image image) {
        this.Hp = hp;
        this.Def = def;
        this.Mana = mana;
        this.inventory = new Inventory();
        this.image = image;
        this.imageView = new ImageView(image);
        x_pos = 0;
        y_pos = 0;

    }

    /**
     *
     * @return current {@code hp} of the entity
     */
    public int getHp() {
        return Hp;
    }

    /**
     *
     * @return current {@code def} of the entity
     */
    public int getDef() {
        return Def;
    }

    /**
     *
     * @return current {@code mana} of the entity
     */
    public int getMana() {
        return Mana;
    }

    /**
     *
     * @return current {@code inventory} of the entity
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     *
     * @return the sprite of the entity
     */
    public Image getImage() {
        return image;
    }

    /**
     * Allows to set the horizon position of the entity
     *
     * @param x_pos The horizontal position of the entity
     */
    public void setX_pos(double x_pos) {
        this.x_pos = x_pos;
        if (this.imageView != null) {
            this.imageView.setLayoutX(x_pos);
        }
    }

    /**
     * Allows to set the vertical position of the entity
     *
     * @param y_pos The vertical position of the entity
     */
    public void setY_pos(double y_pos) {
        this.y_pos = y_pos;
        if (this.imageView != null) {
            this.imageView.setLayoutY(y_pos);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    /**
     *
     * @return The horizontal position of the entity
     */
    public double getX_pos() {
        return x_pos;
    }

    /**
     *
     * @return The vertical position of the entity
     */
    public double getY_pos() {
        return y_pos;
    }

    public void setFacingLeft(boolean left) {
        this.facingLeft = left;
        imageView.setScaleX(-1);
        this.facingRight = false;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public void setFacingRight(boolean right) {
        this.facingRight = right;
        imageView.setScaleX(1);
        this.facingLeft = false;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

}
