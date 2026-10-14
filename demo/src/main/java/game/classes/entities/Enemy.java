package game.classes.entities;

import javafx.scene.image.Image;

public class Enemy extends Entity {

    /**
     *
     * @param Hp Health points of the Enemy
     * @param Def Defense of the Enemy
     * @param Mana Mana of the Enemy
     */

    public Enemy(int Hp, int Def, int Mana, Image image) {
        super(100, 0, 50, image);
    }

    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }
}
