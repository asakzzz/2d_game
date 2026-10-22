package game.classes.playerInteractions;

import java.util.ArrayList;

import game.App;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import game.classes.maps.Map;
import game.classes.movement.playerMovements.MovementControlPlayer;
import game.classes.movement.projectileMovements.MovementControlProjectile;
import game.classes.sprites.ImageReading;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ProjectileShooting {

    private long lastshot = 0;
    // private long cooldown = 300_000_000L;

    /**
     * This method spawns a projectile depending on the cooldown computed by the
     * system time, the key pressed for the direction and the position of the
     * player It then calls the {@code MoveProjectile} method to move it.
     *
     * @param root The root of the window, meant to add or remove elements from
     * it
     * @param scene The scene where the player and projectile are
     * @param player The player who shoots the projectile
     * @param movement The movement controls of the player
     * @param map The map where the scene takes place
     */
    public void SpawnDefaultProjectile(Pane root, Scene scene, Player player, MovementControlPlayer movement, Map map, ArrayList<Projectile> projectileList) {
        long cooldown = player.getAttackCooldown();

        Image sheet = new Image(App.class.getResourceAsStream("assets/knight.png"));
        Image sprite = ImageReading.getFrame(sheet, 0, 0, 32, 32);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            double xVector = (e.getCode() == KeyCode.RIGHT ? 1 : 0) - (e.getCode() == KeyCode.LEFT ? 1 : 0);
            double yVector = (e.getCode() == KeyCode.DOWN ? 1 : 0) - (e.getCode() == KeyCode.UP ? 1 : 0);
            long now = System.nanoTime();

            if (now - lastshot >= cooldown) {
                lastshot = now;
                Projectile projectile = new Projectile(1, 0, 0, sprite, root);
                projectile.setX_pos(player.getX_pos());
                projectile.setY_pos(player.getY_pos());

                // denug
                Rectangle projectileHitbox = projectile.getHitbox();
                projectileHitbox.setFill(Color.TRANSPARENT);
                projectileHitbox.setStroke(Color.RED);
                projectileHitbox.setStrokeWidth(2);

                root.getChildren().add(projectile.getImageView());
                root.getChildren().add(projectileHitbox);// also debug
                projectileList.add(projectile);
                new MovementControlProjectile(movement, map, root).MoveProjectile(projectile, xVector, yVector);

            }

        }
        );

    }
}
