package game.classes.entities;

import javafx.scene.image.Image;

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

    public Player(int Hp, int Def, int Mana , Image image) {
        super(100, 0, 50 , image);
    }
}
