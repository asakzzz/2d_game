package game.classes.maps;

import javafx.scene.canvas.Canvas;

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
     * @return the completed canva This method shapes the canva using the other
     * method above
     */
    public Canvas CreateCanva() {
        Map map = new Map(this.tiles);
        return MapRenderer.render(map);
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

}
