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
import javafx.scene.shape.Rectangle;

public class ProjectileShooting {

    public boolean keyDown = false;

    public void SpawnDefaultProjectile(Pane root, Scene scene, Player player , MovementControlPlayer movement , CreateMap map) {

        //for testing purposes
        Image sheet = new Image(App.class.getResourceAsStream("assets/knight.png"));
        Image sprite = ImageReading.getFrame(sheet, 0, 0, 32, 32);

        //make the projectile appear if space pressed
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE && !keyDown) {
                keyDown = true;
                Projectile projectile = new Projectile(1, 0, 0, sprite);
                MovementControlProjectile projectileMovement = new MovementControlProjectile(movement , map, root);
                Rectangle hitbox = projectile.getHitbox();
                projectile.setX_pos(player.getX_pos());
                projectile.setY_pos(player.getY_pos());

                hitbox.setFill(javafx.scene.paint.Color.TRANSPARENT);
                hitbox.setStroke(javafx.scene.paint.Color.GREEN);
                hitbox.setStrokeWidth(2);

                root.getChildren().add(projectile.getImageView());
                projectileMovement.MoveProjectile(projectile);

            }

        }
        );
        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.SPACE) {
                keyDown = false;

            }
        });

    }
}
