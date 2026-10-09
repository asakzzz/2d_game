package game.classes.movement;

import java.util.HashSet;
import java.util.Set;

import game.classes.entities.Player;
import game.classes.maps.CreateMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * This class allows the player to control their character
 */
public class MovementControlPlayer {

    private final Player player;
    private final Scene scene;
    private final Set<KeyCode> keys = new HashSet<>();
    private final CreateMap map;
    private double horizontalSpeed = 0;
    private double verticalSpeed = 0;
    private final double speed = 0.8;

    /**
     * This creates the class MovementInputControls, which we will call later to
     * control the player
     *
     * @param player The player we want to move
     * @param scene The scene where the player is
     */
    public MovementControlPlayer(Player player, Scene scene, CreateMap map) {
        this.player = player;
        this.scene = scene;
        this.map = map;
    }

    /**
     * This method allows us to control the user. We first add/remove the keys
     * which are pressed/released We then create a timer with
     * {@code AnimationTimer}, allowing us to move diagonally and overall
     * smoothly This timer updates every 1/60 second, basically each frame
     * Depending on the key pressed and if there is a wall in front of it, we
     * move the player. The movement is made by the {@code maxSpeed} and the
     * {@code ACCEL} params the ACCEL make the player move faster and faster,
     * the maxSpeed caps it to avoid goind mach 20
     */
    public void handleInput() {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> keys.add(e.getCode()));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> keys.remove(e.getCode()));

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                double width = player.getWidth();
                double height = player.getHeight();

                int xVector = (keys.contains(KeyCode.D) ? 1 : 0) - (keys.contains(KeyCode.Q) ? 1 : 0);
                int yVector = (keys.contains(KeyCode.S) ? 1 : 0) - (keys.contains(KeyCode.Z) ? 1 : 0);

                if (xVector < 0) {
                    player.setFacingLeft(true);
                } 
                
                if (xVector > 0) {
                    player.setFacingRight(true);
                }

                double len = Math.hypot(xVector, yVector);

                if (len == 0) {
                    horizontalSpeed = 0;
                    verticalSpeed = 0;
                } else {
                    horizontalSpeed = xVector / len * speed;
                    verticalSpeed = yVector / len * speed;
                }

                if (horizontalSpeed != 0 && !map.isWall(player.getX_pos() + horizontalSpeed, player.getY_pos(), width, height)) {
                    player.setX_pos(player.getX_pos() + horizontalSpeed);
                }

                if (verticalSpeed != 0 && !map.isWall(player.getX_pos(), player.getY_pos() + verticalSpeed, width, height)) {
                    player.setY_pos(player.getY_pos() + verticalSpeed);
                }

            }
        }
                .start();
    }

    /**
     *
     * @return a double, the total speed of the player
     */
    public double getSpeed() {
        return Math.hypot(horizontalSpeed, verticalSpeed);
    }

    public double getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public double getVerticalSpeed() {
        return verticalSpeed;
    }

    /**
     *
     * @return a boolean, checking if the player is moving or not
     */
    public boolean isMoving() {

        return this.getSpeed() > 0;
    }

}
