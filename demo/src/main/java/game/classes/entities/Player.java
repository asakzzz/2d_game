package game.classes.entities;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * This create a {@code Player} class extending the {@code Entity} class
 */ 

public class Player extends Entity {

    /**
     * 
     * @param Hp Health points of the player
     * @param Def Defense of the player
     * @param Mana Mana of the player
     */

    public Player(int Hp, int Def, int Mana , Image image , Pane root) {
        super(5, 0, 50 , image , root);
    }


    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }
}
