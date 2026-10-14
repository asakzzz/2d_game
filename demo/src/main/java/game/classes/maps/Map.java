package game.classes.maps;

public class Map {

    public static final int tileSize = 64;

    public static int getTileSize() {
        return tileSize;
    }
    private final int[][] tiles;

    public Map(int[][] tiles) {
        this.tiles = tiles;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public boolean isWall(double x, double y) {
        return getTile(x, y) == TileType.WALL.getId();
    }

    /**
     *
     * @param x The x position of the player
     * @param y The y position of the player
     * @param width The witdth of the hitbox
     * @param height The height of the hitbox
     * @return A boolean depending on isWall and the side of the hitbox being
     * detected
     */
    public boolean isWall(double x, double y, double width, double height) {
        int firstCol = (int) Math.floor(x / tileSize);
        int lastCol = (int) Math.floor((x + width - 0.01) / tileSize);
        int firstRow = (int) Math.floor(y / tileSize);
        int lastRow = (int) Math.floor((y + height - 0.01) / tileSize);

        for (int row = firstRow; row <= lastRow; row++) {
            for (int col = firstCol; col <= lastCol; col++) {
                if (isWall(col * tileSize + 1, row * tileSize + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDoor(double x, double y) {
        return getTile(x, y) == TileType.DOOR.getId();
    }

    /**
     *
     * @param x The x position of the player
     * @param y The y position of the player
     * @param width The witdth of the hitbox
     * @param height The height of the hitbox
     * @return A boolean depending on isDoor and the side of the hitbox being
     * detected
     */
    public boolean isDoor(double x, double y, double width, double height) {
        int firstCol = (int) Math.floor(x / tileSize);
        int lastCol = (int) Math.floor((x + width - 0.01) / tileSize);
        int firstRow = (int) Math.floor(y / tileSize);
        int lastRow = (int) Math.floor((y + height - 0.01) / tileSize);

        for (int row = firstRow; row <= lastRow; row++) {
            for (int col = firstCol; col <= lastCol; col++) {
                if (isDoor(col * tileSize + 1, row * tileSize + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getTile(double x, double y) {
        int col = (int) (x / tileSize);
        int row = (int) (y / tileSize);

        if (row < 0 || col < 0
                || row >= tiles.length
                || col >= tiles[0].length) {
            return TileType.WALL.getId();
        }

        return tiles[row][col];
    }
}
