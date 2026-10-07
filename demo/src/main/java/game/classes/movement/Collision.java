package game.classes.movement;

import java.util.ArrayList;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import javafx.scene.shape.Rectangle;

public class Collision<T> {

    //use same system as isWall override in map
    public ArrayList<Rectangle> getHitboxes(Player player, T otherEntity) {
        Rectangle playerHitbox = player.getHitbox();
        ArrayList<Rectangle> hitboxList = new ArrayList<Rectangle>(2);

        hitboxList.add(playerHitbox);

        if (otherEntity instanceof Enemy) {
            hitboxList.add(((Enemy) otherEntity).getHitbox());
        }

        if (otherEntity instanceof Projectile) {
            hitboxList.add(((Projectile) otherEntity).getHitbox());
        }

        return hitboxList;
    }

    public boolean CollidingHitbox(Player player , T otherEntity) {

        ArrayList<Rectangle> hitboxList = getHitboxes(player, otherEntity);
        Rectangle playerHitbox = hitboxList.get(0);
        Rectangle otherHitbox = hitboxList.get(1);

        return playerHitbox.intersects(otherHitbox.getBoundsInParent());

    }

}
