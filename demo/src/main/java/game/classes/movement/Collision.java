package game.classes.movement;

import java.util.ArrayList;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import javafx.scene.shape.Rectangle;

public class Collision<T> {

    /**
     * This method initialize an array of hitboxes with the player's hitbox and
     * another undefinied entity hitbox.
     *
     * @param player The player we want to check for hitbox
     * @param otherEntity Another entity (enemy, projectile ...)
     * @return An arraylist of the hitboxes of the two entities
     */
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

    public ArrayList<Rectangle> getHitboxes(Enemy enemy, T otherEntity) {
        Rectangle enemyHitbox = enemy.getHitbox();
        ArrayList<Rectangle> hitboxList = new ArrayList<Rectangle>(2);

        hitboxList.add(enemyHitbox);

        if (otherEntity instanceof Enemy) {
            hitboxList.add(((Enemy) otherEntity).getHitbox());
        }

        if (otherEntity instanceof Projectile) {
            hitboxList.add(((Projectile) otherEntity).getHitbox());
        }

        return hitboxList;
    }

    /**
     * This method check if the hitboxes in the list collides
     *
     * @param player The player who we want to check the hitbox
     * @param otherEntity The entity which we want to check the hitbox
     * @return A boolean, true if the hitboxes collide, false otherwise
     */
    public boolean CollidingHitbox(Player player, T otherEntity) {

        ArrayList<Rectangle> hitboxList = getHitboxes(player, otherEntity);
        Rectangle playerHitbox = hitboxList.get(0);
        Rectangle otherHitbox = hitboxList.get(1);

        return playerHitbox.intersects(otherHitbox.getBoundsInParent());

    }

    public boolean CollidingHitbox(Enemy enemy, T otherEntity) {

        ArrayList<Rectangle> hitboxList = getHitboxes(enemy, otherEntity);
        Rectangle playerHitbox = hitboxList.get(0);
        Rectangle otherHitbox = hitboxList.get(1);

        return playerHitbox.intersects(otherHitbox.getBoundsInParent());

    }
}
