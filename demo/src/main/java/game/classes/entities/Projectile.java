package game.classes.entities;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Projectile extends Entity {

    /**
     *
     * @param Hp Health points of the Projectile
     */
    public Projectile(int Hp, int Def, int Mana, Image image, Pane root) {
        super(1, 0, 0, image, root);
    }

    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }
}
