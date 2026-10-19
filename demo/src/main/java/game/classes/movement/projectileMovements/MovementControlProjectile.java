package game.classes.movement.projectileMovements;

import game.classes.entities.Projectile;
import game.classes.maps.Map;
import game.classes.movement.playerMovements.MovementControlPlayer;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class MovementControlProjectile {

    private double xVector = 0;
    private double yVector = 0;
    private double speed = 1.2;

    MovementControlPlayer movement;
    Map map;
    Pane root;

    /** This constructors allows each projectile to move independently from each other
     * 
     * @param movement The movement of the player to keep its inertia
     * @param map The map where the player is, used here for collisions
     * @param root The root to add elements in the window
     */

    public MovementControlProjectile(MovementControlPlayer movement, Map map, Pane root) {
        this.movement = movement;
        this.map = map;
        this.root = root;
    }

    /** This method computes a vector depending on the player's speed, then moves the projectile keeping those vectors in mind
     * If the projectile hits a wall, it is killed
     * 
     * @param projectile The projectile we want to move
     * @param directionX The direction where we want to shoot the projectile
     * @param directionY The direction where we want to shoot the projectile
     */

    public void MoveProjectile(Projectile projectile, double directionX, double directionY) {

        double len = Math.hypot(directionX, directionY);
        xVector = directionX / len * speed + movement.getHorizontalSpeed();
        yVector = directionY / len * speed + movement.getVerticalSpeed();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                System.out.println( "X" + xVector);
                System.out.println("Y" + yVector);
                projectile.setX_pos(projectile.getX_pos() + xVector);
                projectile.setY_pos(projectile.getY_pos() + yVector);
                
                if (hitsWall(projectile) || hitsDoor(projectile)) {
                    projectile.kill();
                    stop();
                }

            }
        }.start();

    }

    /**
     * 
     * @param projectile The projectile we want to check
     * @return A boolean, true if the projectile hits a wall, false otherwise
     */

    private boolean hitsWall(Projectile projectile) {
        return map.isWall(projectile.getX_pos(), projectile.getY_pos(),
                projectile.getWidth(), projectile.getHeight());
    }

    private boolean hitsDoor(Projectile projectile) {
        return map.isDoor(projectile.getX_pos(), projectile.getY_pos(), projectile.getWidth(), projectile.getHeight());
    }
}
