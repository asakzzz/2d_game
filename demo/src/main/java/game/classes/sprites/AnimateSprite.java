package game.classes.sprites;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import javafx.scene.image.Image;

public class AnimateSprite<T> {

    private final long frameDuration;
    private int current = 0;
    private long lastSwitch = 0;

    /**
     * This method set the frameduration of the class depending on the
     * spritesheet.
     *
     * @param fps frame per seconds of the spritesheets
     */
    public AnimateSprite(double fps) {
        this.frameDuration = (long) (1_000_000_000 / fps);
    }

    /**
     * This method animates the player depending on the last frame and the
     * frameduration. If the requirements are met, the next frame on the
     * spritesheet is set on the player's image.
     *
     * @param player The player we want to animate
     * @param frames the frames we want to animate the player with
     * @param now Now (system clock)
     */
    public void animate(T entity, Image[] frames, long now) {
        if (entity instanceof Player) {

            if (now - lastSwitch >= frameDuration) {
                current = (current + 1) % frames.length;
                ((Player) entity).setImage(frames[current]);
                lastSwitch = now;
            }
        }

        if (entity instanceof Enemy) {

            if (now - lastSwitch >= frameDuration) {
                current = (current + 1) % frames.length;
                ((Enemy) entity).setImage(frames[current]);
                lastSwitch = now;
            }
        }

        if (entity instanceof Projectile) {

            if (now - lastSwitch >= frameDuration) {
                current = (current + 1) % frames.length;
                ((Projectile) entity).setImage(frames[current]);
                lastSwitch = now;
            }
        }

    }

    /**
     * This method is here in case of a need to reset the animation in progress.
     * Could be for debug or glitchy transitions
     *
     * @param player The player we want to reset the frames of
     * @param frames The set of frames we want to reset from
     */
    public void reset(T entity, Image[] frames) {
        if (entity instanceof Player) {
            current = 0;
            ((Player) entity).setImage(frames[0]);
        }
        if (entity instanceof Enemy) {
            current = 0;
            ((Enemy) entity).setImage(frames[0]);
        }
        if (entity instanceof Projectile) {
            current = 0;
            ((Projectile) entity).setImage(frames[0]);
        }

    }
}
