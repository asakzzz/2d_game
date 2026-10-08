package game.classes.movement;

import game.classes.entities.Projectile;
import game.classes.maps.CreateMap;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class MovementControlProjectile {

    MovementControlPlayer movement;
    CreateMap map;
    Pane root;

    public MovementControlProjectile(MovementControlPlayer movement, CreateMap map, Pane root) {
        this.movement = movement;
        this.map = map;
        this.root = root;
    }

    public void MoveProjectile(Projectile projectile) {
        double projectileX = movement.getHorizontalSpeed();
        double projectileY = movement.getVerticalSpeed();

        if (projectileX == 0 && projectileY == 0) {
            projectileX = 3;
        }

        double len = Math.hypot(projectileX, projectileY);
        final double xVector = projectileX / len * 3;
        final double yVector = projectileY / len * 3;

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


    private boolean hitsWall(Projectile projectile) {
        return map.isWall(projectile.getX_pos(), projectile.getY_pos(),
                projectile.getWidth(), projectile.getHeight());
    }
}
