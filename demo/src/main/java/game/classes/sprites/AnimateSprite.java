package game.classes.sprites;

import game.classes.entities.Player;
import javafx.scene.image.Image;

public class AnimateSprite {

    private final long frameDuration;
    private int current = 0;
    private long lastSwitch = 0;

    /**
     * 
     * @param fps frame per seconds of the spritesheets
     */

    public AnimateSprite(double fps) {
        this.frameDuration = (long) (1_000_000_000 / fps);
    }

    /**
     * 
     * @param player The player we want to animate
     * @param frames the frames we want to animate the player with
     * @param now Now (system clock)
     */

    public void animate(Player player, Image[] frames, long now) {
        if (now - lastSwitch >= frameDuration) {
            current = (current + 1) % frames.length;
            player.setImage(frames[current]);
            lastSwitch = now;
        }
    }

    /**
     * 
     * @param player The player we want to reset the frames of
     * @param frames The set of frames we want to reset from
     */

    public void reset(Player player, Image[] frames) {
        current = 0;
        player.setImage(frames[0]);
    }
}