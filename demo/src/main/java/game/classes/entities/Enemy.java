package game.classes.entities;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends Entity {

    /**
     *
     * @param Hp Health points of the Enemy
     * @param Def Defense of the Enemy
     * @param Mana Mana of the Enemy
     */

    public Enemy(int Hp, int Def, int Mana, Image image , Pane root) {
        super(3, 0, 50, image , root);
    }

    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }
}
