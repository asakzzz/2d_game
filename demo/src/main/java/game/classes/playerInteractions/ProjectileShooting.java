package game.classes.playerInteractions;

import game.App;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import game.classes.maps.CreateMap;
import game.classes.movement.MovementControlPlayer;
import game.classes.movement.MovementControlProjectile;
import game.classes.sprites.ImageReading;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class ProjectileShooting {

    private long lastshot = 0;
    private long cooldown = 300_000_000L;

    /**
     * 
     * @param root The root of the window, meant to add or remove elements from it
     * @param scene The scene where the player and projectile are
     * @param player The player who shoots the projectile
     * @param movement The movement controls of the player
     * @param map The map where the scene takes place
     */

    public void SpawnDefaultProjectile(Pane root, Scene scene, Player player, MovementControlPlayer movement, CreateMap map) {

        //for testing purposes
        Image sheet = new Image(App.class.getResourceAsStream("assets/knight.png"));
        Image sprite = ImageReading.getFrame(sheet, 0, 0, 32, 32);

        //make the projectile appear if space pressed
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            double xVector = (e.getCode() == KeyCode.RIGHT ? 1 : 0) - (e.getCode() == KeyCode.LEFT ? 1 : 0);
            double yVector = (e.getCode() == KeyCode.DOWN ? 1 : 0) - (e.getCode() == KeyCode.UP ? 1 : 0);
            long now = System.nanoTime();

            if (now - lastshot >= cooldown) {
                lastshot = now;
                Projectile projectile = new Projectile(1, 0, 0, sprite);
                projectile.setX_pos(player.getX_pos());
                projectile.setY_pos(player.getY_pos());
                root.getChildren().add(projectile.getImageView());

                new MovementControlProjectile(movement, map, root).MoveProjectile(projectile, xVector, yVector);
            }

        }
        );

    }
}
