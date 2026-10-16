package game.classes.enemyShooting;

import java.util.ArrayList;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import game.classes.maps.Map;
import game.classes.movement.playerMovements.MovementControlPlayer;
import game.classes.movement.projectileMovements.MovementControlProjectile;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class EnemyShooting {

    private long lastshot = 0;
    private long cooldown = 1_000_000_000L;

    public void enemyShooting(Enemy enemy, Pane root, Player player, MovementControlPlayer movement, Map map , Image sprite , ArrayList<Projectile> projectileList) {

        long now = System.nanoTime();
        double xVector = player.getX_pos() - enemy.getX_pos();
        double yVector = player.getY_pos() - enemy.getY_pos();

        if (now - lastshot >= cooldown) {
            lastshot = now;
            Projectile projectile = new Projectile(1, 0, 0, sprite , root);
            projectile.setX_pos(enemy.getX_pos());
            projectile.setY_pos(enemy.getY_pos());
            root.getChildren().add(projectile.getImageView());
            projectileList.add(projectile);
            new MovementControlProjectile(movement, map, root).MoveProjectile(projectile, xVector, yVector);
        }

    }
}
