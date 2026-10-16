package game.classes.movement.enemyMovements;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.maps.Map;

public class EnemyFleePlayer {
    private double speed = 0.5;

    /** This method makes the enemy chase the player, calculating the distance between them.
     * It checks if the enemy is on the player, if not it moves the enemy towards it. 
     * 
     * @param enemy The enemy we want to move
     * @param player The player we want the enemy to move to
     * @param map the map where the scene takes place
     */

    public void enemyFleePlayer(Enemy enemy, Player player, Map map) {

        double xVector = enemy.getX_pos() - player.getX_pos();
        double yVector = enemy.getY_pos() - player.getY_pos();

        double distance = Math.hypot(xVector, yVector);

        if (distance >= 400) {
            return;
        }

        double stepX = xVector / distance * speed;
        double stepY = yVector / distance * speed;

        if (!map.isWall(enemy.getX_pos(), enemy.getY_pos() + stepY, enemy.getWidth(), enemy.getHeight())) {
            enemy.setY_pos(enemy.getY_pos() + stepY);
        }

        if (!map.isWall(enemy.getX_pos() + stepX, enemy.getY_pos(), enemy.getWidth(), enemy.getHeight())) {
            enemy.setX_pos(enemy.getX_pos() + stepX);

        }

    }
}
