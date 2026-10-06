package game.classes.maps;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * MotherMap is the blueprint for all future maps
 */
public class CreateMap {

    /**
     * {@code tileSize} tells the code the size of a cell in pixel
     */
    public static final int tileSize = 64;

    // 0 = ground ,1 = wall, 2 = door
    /**
     * {@code tiles} is the layout of the map 1 means a wall cell, 0 a wall cell
     * and 2 a door
     */
    protected int[][] tiles = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public int[][] getTiles() {
        return tiles;
    }

    public void loadTIles(int[][] newTiles) {
        this.tiles = newTiles;
    }

    /**
     *
     * @param tiles Prebuillt map passed in parameter to be drawn into the
     * window
     * @return the completed canva This method shapes the canva to the right
     * size (tiles * tileSize), then itters through the matrix It detects if the
     * cell is a wall or the gound depending on the number It finally draws the
     * cell depending on the type
     */
    public Canvas CreateCanva(int[][] tiles) {
        int rows = tiles.length;
        int cols = tiles[0].length;
        Canvas canvas = new Canvas(cols * tileSize, rows * tileSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (tiles[row][col] == 0) {
                    gc.setFill(Color.ALICEBLUE);
                }

                if (tiles[row][col] == 1) {
                    gc.setFill(Color.CRIMSON);
                }

                if (tiles[row][col] == 2) {
                    gc.setFill(Color.GREEN);
                }

                if (tiles[row][col] == 3) {
                    gc.setFill(Color.BROWN);
                }
                gc.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
        return canvas;
    }

    /**
     *
     * @return the completed canva This method shapes the canva using the other
     * method above
     */
    public Canvas CreateCanva() {

        return CreateCanva(this.tiles);
    }

    /**
     *
     * @param x The x position of the player
     * @param y The y position of the player
     * @return boolean (true if wall, false otherwise)
     */
    public boolean isWall(double x, double y) {
        int col = (int) (x / tileSize);
        int row = (int) (y / tileSize);

        if (col < 0 || row < 0 || row >= tiles.length || col >= this.tiles[0].length) {
            return true;
        }
        return this.tiles[row][col] == 1;
    }

    public boolean isWall(double x, double y, double width, double height) {
        double right = x + width - 0.003;
        double bottom = y + height - 0.003;

        boolean topLeft = isWall(x, y);
        boolean topRight = isWall(right, y);
        boolean bottomLeft = isWall(x, bottom);
        boolean bottomRight = isWall(right, bottom);

        return topLeft || topRight || bottomLeft || bottomRight;
    }

    /**
     *
     * @param x The x position of the player
     * @param y The y position of the player
     * @return boolean (true if door, false otherwise)
     */
    public boolean isDoor(double x, double y) {
        int col = (int) (x / tileSize);
        int row = (int) (y / tileSize);
        if (col < 0 || row < 0 || row >= tiles.length || col >= this.tiles[0].length) {
            return false;
        }
        return this.tiles[row][col] == 2;
    }

    public boolean isDoor(double x, double y, double width, double height) {
        double right = x + width - 0.001;
        double bottom = y + height - 0.001;

        boolean topLeft = isWall(x, y);
        boolean topRight = isWall(right, y);
        boolean bottomLeft = isWall(x, bottom);
        boolean bottomRight = isWall(right, bottom);

        return topLeft || topRight || bottomLeft || bottomRight;
    }

    /**
     *
     * @param x The x position of the player
     * @param y The y position of the player
     * @return boolean (true if previous door, false otherwise)
     */
    public boolean isPreviousDoor(double x, double y) {
        int col = (int) (x / tileSize);
        int row = (int) (y / tileSize);

        if (col < 0 || row < 0 || row >= tiles.length || col >= this.tiles[0].length) {
            return false;
        }
        return this.tiles[row][col] == 3;
    }

    public boolean isPreviousDoor(double x, double y, double width, double height) {
        double right = x + width - 0.003;
        double bottom = y + height - 0.003;

        boolean topLeft = isWall(x, y);
        boolean topRight = isWall(right, y);
        boolean bottomLeft = isWall(x, bottom);
        boolean bottomRight = isWall(right, bottom);

        return topLeft || topRight || bottomLeft || bottomRight;
    }

}
