package game.classes.sprites;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

/**
 * 
 * ImageReading read a square of pixels from the {@code sheet} thanks to the reader. 
 * It then sends thoses pixels to the {@code WritableImage} to create a sprite out of the spritesheet
 */

public class ImageReading {
    public static Image getFrame(Image sheet, int col, int row, int frameWidth, int frameHeigh) {
        PixelReader reader = sheet.getPixelReader();
        return new WritableImage(reader, col * frameWidth, row * frameHeigh, frameWidth, frameHeigh);
    }
}
