package game.classes.movement.enemyMovements;

import java.util.Random;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.maps.CreateMap;

public class EnemyRandomMovement {

    private Random r = new Random();
    private double speed = 0.6;

    private double dirX = 0;
    private double dirY = 0;
    private long nextChange = 0;

    /** This methods moves the enemy randomly when close enough to the player, simulating it fleeing (ranged enemy)
     * It checks if the distance between the player and the enemy is big enough
     * if yes, it picks a random new direction using random angles and moves towards it.
     * if not, it stays in places
     * 
     * @param now The system time when the method is called
     */

    private void pickNewDirection(long now) {
        double angle = r.nextDouble() * 2 * Math.PI;
        dirX = Math.cos(angle);
        dirY = Math.sin(angle);
        nextChange = now + 500_000_000L + (long) (r.nextDouble() * 1_500_000_000L); // random for 0.5 to 2 secs
    }

    /**
     * 
     * @param enemy The enemy we want to move
     * @param player The player we want the enemy to avoid
     * @param map The map where the scene takes place
     * @param now The system time when the method is called
     */

    public void enemyRandomMovement(Enemy enemy, Player player, CreateMap map, long now) {
        //Comments cause I struggle with math

        double enemyX = enemy.getX_pos(); //x1
        double enemyY = enemy.getY_pos(); // y1

        double playerX = player.getX_pos(); //x2
        double playerY = player.getY_pos(); //y2

        double ac = Math.abs(playerY - enemyY);
        double cb = Math.abs(playerX - enemyX);

        double distance = Math.hypot(ac, cb);

        if (distance > 300) {

            return;
        }

        if (now >= nextChange) {
            pickNewDirection(now);
        }

        double stepX = dirX * speed;
        double stepY = dirY * speed;

        if (!map.isWall(enemy.getX_pos(), enemy.getY_pos() + stepY, enemy.getWidth(), enemy.getHeight())) {
            enemy.setY_pos(enemy.getY_pos() + stepY);
        } else {
            nextChange = 0;
        }

        if (!map.isWall(enemy.getX_pos() + stepX, enemy.getY_pos(), enemy.getWidth(), enemy.getHeight())) {
            enemy.setX_pos(enemy.getX_pos() + stepX);

        } else {
            nextChange = 0;
        }

    }
}
