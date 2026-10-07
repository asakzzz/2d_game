package game.classes.maps;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MapRenderer {

    public static final int TILE_SIZE = 64;

    static public Canvas render(Map map) {
        int[][] tiles = map.getTiles();

        int rows = tiles.length;
        int cols = tiles[0].length;

        Canvas canvas = new Canvas(
            cols * TILE_SIZE,
            rows * TILE_SIZE
        );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                gc.setFill(getColor(tiles[row][col]));

                gc.fillRect(
                    col * TILE_SIZE,
                    row * TILE_SIZE,
                    TILE_SIZE,
                    TILE_SIZE
                );
            }
        }

        return canvas;
    }

    static private Color getColor(int tile) {
        return switch (tile) {
            case 0 -> Color.ALICEBLUE;
            case 1 -> Color.CRIMSON;
            case 2 -> Color.GREEN;
            case 3 -> Color.BROWN;
            default -> Color.BLACK;
        };
    }
}
