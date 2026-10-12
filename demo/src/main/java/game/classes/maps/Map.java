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

    public boolean isDoor(double x, double y) {
        return getTile(x, y) == TileType.DOOR.getId();
    }

    private int getTile(double x, double y) {
        int col = (int) (x / tileSize);
        int row = (int) (y / tileSize);

        if (row < 0 || col < 0 ||
            row >= tiles.length ||
            col >= tiles[0].length) {
            return TileType.WALL.getId();
        }

        return tiles[row][col];
    }
}
