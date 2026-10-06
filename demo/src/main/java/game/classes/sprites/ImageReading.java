package game.classes.sprites;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 *
 * ImageReading read a square of pixels from the {@code sheet} thanks to the
 * reader. It then sends thoses pixels to the {@code WritableImage} to create a
 * sprite out of the spritesheet
 */
public class ImageReading {

    /**
     * 
     * @param sheet The sprite sheet
     * @param col The column of the sprite in the sheet
     * @param row The row of the sprite in the sheet
     * @param frameWidth The width of the frame
     * @param frameHeigh The heigh of the frame
     * @return A new Image with the sprite inside
     */

    public static Image getFrame(Image sheet, int col, int row, int frameWidth, int frameHeigh) {
        PixelReader reader = sheet.getPixelReader();
        return new WritableImage(reader, col * frameWidth, row * frameHeigh, frameWidth, frameHeigh);
    }

    /**
     * 
     * @param sheet The animation sheet
     * @param col The column where the animation resides
     * @param row The column where the animation starts
     * @param n The number of times need to itter through the col for the full animation
     * @param frameWidth The width of the frame
     * @param frameHeigh The heigh of the frame
     * @return An array with the Images inside
     */

    public static Image[] getAnimation(Image sheet, int col, int row, int n, int frameWidth, int frameHeigh) {
        Image[] frames = new Image[n];

        for (int i = 0; i < n; i++) {
            frames[i] = getFrame(sheet, col + i, row, frameWidth, frameHeigh);
        }

        return frames;

    }
}
