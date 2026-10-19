package game.classes.healthCare;

import java.util.ArrayList;
import java.util.Iterator;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import game.classes.movement.Collision;
import javafx.scene.layout.Pane;

public class DealDamage<T> {

    public void dealDamage(Player player, Enemy enemy, ArrayList<Projectile> projectilePlayer, ArrayList<Projectile> projectileEnemy, Pane root) {
        KillEntity killEntity = new KillEntity();
        killEntity.killPlayer(player, root);
        killEntity.killEnemy(enemy, root);
        Collision collision = new Collision();

        Iterator<Projectile> it = projectileEnemy.iterator();
        while (it.hasNext()) {
            Projectile projectile = it.next();
            if (projectile.isAlive() == false) {
                it.remove();
                continue;
            }

            if (collision.CollidingHitbox(player, projectile)) {
                player.decreaseHpSoft();
                System.out.println("player" + player.getHp());
                projectile.kill();
                it.remove();
            }
        }

        it = projectilePlayer.iterator();

        while (it.hasNext()) {
            Projectile projectile = it.next();
            if (projectile.isAlive() == false) {
                it.remove();
                continue;
            }

            if (collision.CollidingHitbox(enemy, projectile)) {
                enemy.decreaseHpSoft();
                System.out.println("enemy" + enemy.getHp());
                projectile.kill();
                it.remove();
            }
        }
    }

}
