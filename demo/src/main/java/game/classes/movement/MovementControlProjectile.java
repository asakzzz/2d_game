package game.classes.movement;

import game.classes.entities.Projectile;
import game.classes.maps.CreateMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MovementControlProjectile {

    private double xVector = 0;
    private double yVector = 0;

    MovementControlPlayer movement;
    CreateMap map;
    Pane root;

    public MovementControlProjectile(MovementControlPlayer movement, CreateMap map, Pane root, Scene scene) {
        this.movement = movement;
        this.map = map;
        this.root = root;
    }

    public void MoveProjectile(Projectile projectile, Scene scene, double directionX, double directionY) {

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

    private boolean hitsWall(Projectile projectile) {
        return map.isWall(projectile.getX_pos(), projectile.getY_pos(),
                projectile.getWidth(), projectile.getHeight());
    }
}
