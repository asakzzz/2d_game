package game.classes.movement;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.maps.CreateMap;

public class MovementControlEnemy {

    private double speed = 0.4;

    public void enemyNearestNeighbor(Enemy enemy, Player player, CreateMap map) {

        double xVector = player.getX_pos() - enemy.getX_pos();
        double yVector = player.getY_pos() - enemy.getY_pos();

        double distance = Math.hypot(xVector, yVector);

        if (distance <= speed) {
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
