package game.classes.entities;

import javafx.scene.image.Image;

public class Projectile extends Entity {

        /**
     *
     * @param Hp Health points of the Projectile
     */

    public Projectile(int Hp, int Def, int Mana, Image image) {
        super(1, 0, 0,image);
    }

    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }
}
