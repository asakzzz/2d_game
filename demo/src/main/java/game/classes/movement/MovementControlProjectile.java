package game.classes.movement;

import game.classes.entities.Projectile;
import game.classes.maps.CreateMap;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class MovementControlProjectile {

    private double xVector = 0;
    private double yVector = 0;

    MovementControlPlayer movement;
    CreateMap map;
    Pane root;

    /**
     * 
     * @param movement The movement of the player to keep its inertia
     * @param map The map where the player is, used here for collisions
     * @param root The root to add elements in the window
     */

    public MovementControlProjectile(MovementControlPlayer movement, CreateMap map, Pane root) {
        this.movement = movement;
        this.map = map;
        this.root = root;
    }

    /**
     * 
     * @param projectile The projectile we want to move
     * @param directionX The direction where we want to shoot the projectile
     * @param directionY The direction where we want to shoot the projectile
     */

    public void MoveProjectile(Projectile projectile, double directionX, double directionY) {

        double len = Math.hypot(directionX, directionY);
        xVector = directionX / len * 3;
        yVector = directionY / len * 3;

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                projectile.setX_pos(projectile.getX_pos() + xVector);
                projectile.setY_pos(projectile.getY_pos() + yVector);
                if (hitsWall(projectile)) {
                    root.getChildren().remove(projectile.getImageView());
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
}
