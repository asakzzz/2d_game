package game.classes.sprites;

import game.classes.entities.Player;
import javafx.scene.image.Image;

public class AnimateSprite {

    private final long frameDuration;
    private int current = 0;
    private long lastSwitch = 0;

    public AnimateSprite(double fps) {
        this.frameDuration = (long) (1_000_000_000 / fps);
    }

    public void animate(Player player, Image[] frames, long now) {
        if (now - lastSwitch >= frameDuration) {
            current = (current + 1) % frames.length;
            player.setImage(frames[current]);
            lastSwitch = now;
        }
    }

    public void reset(Player player, Image[] frames) {
        current = 0;
        player.setImage(frames[0]);
    }
}