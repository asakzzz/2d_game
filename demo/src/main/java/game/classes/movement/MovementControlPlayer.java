package game.classes.movement;

import java.util.HashSet;
import java.util.Set;

import game.classes.entities.Player;
import game.classes.maps.CreateMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

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
    private final double ACCEL = 1;
    private final double maxSpeed = 6;

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
        scene.setOnKeyPressed(KeyEvent -> keys.add(KeyEvent.getCode()));
        scene.setOnKeyReleased(KeyEvent -> keys.remove(KeyEvent.getCode()));

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (!keys.contains(KeyCode.D) && !keys.contains(KeyCode.Q)) {
                    horizontalSpeed = 0;
                }

                if (!keys.contains(KeyCode.Z) && !keys.contains(KeyCode.S)) {
                    verticalSpeed = 0;
                }

                if (keys.contains(KeyCode.D) && map.isWall(player.getX_pos() + 10, player.getY_pos()) == false) {
                    if (horizontalSpeed >= maxSpeed) {
                        player.setX_pos(player.getX_pos() + maxSpeed);
                        player.setFacingRight(true);
                    } else {
                        horizontalSpeed += ACCEL;
                        player.setX_pos(player.getX_pos() + horizontalSpeed);

                    }

                }
                if (keys.contains(KeyCode.Q) && map.isWall(player.getX_pos() - 10, player.getY_pos()) == false) {
                    if (horizontalSpeed >= maxSpeed) {
                        player.setX_pos(player.getX_pos() - maxSpeed);
                        player.setFacingLeft(true);
                    } else {
                        horizontalSpeed += ACCEL;
                        player.setX_pos(player.getX_pos() - horizontalSpeed);
                    }
                }

                if (keys.contains(KeyCode.Z) && map.isWall(player.getX_pos(), player.getY_pos() - 10) == false) {
                    if (verticalSpeed >= maxSpeed) {
                        player.setY_pos(player.getY_pos() - maxSpeed);
                    } else {
                        verticalSpeed += ACCEL;
                        player.setY_pos(player.getY_pos() - verticalSpeed);
                    }
                }

                if (keys.contains(KeyCode.S) && map.isWall(player.getX_pos(), player.getY_pos() + 10) == false) {
                    if (verticalSpeed >= maxSpeed) {
                        player.setY_pos(player.getY_pos() + maxSpeed);
                    } else {
                        verticalSpeed += ACCEL;
                        player.setY_pos(player.getY_pos() + verticalSpeed);
                    }
                }

                double total = horizontalSpeed + verticalSpeed;
                if (total > maxSpeed) {
                    horizontalSpeed = 4;
                    verticalSpeed = 4;
                }

            }
        }.start();
    }

    /**
     *
     * @return a double, the total speed of the player
     */
    public double getSpeed() {
        return Math.hypot(horizontalSpeed, verticalSpeed);
    }

    /**
     *
     * @return a boolean, checking if the player is moving or not
     */
    public boolean isMoving() {

        return this.getSpeed() > 0;
    }

}
