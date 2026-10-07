package game.classes.maps;

public class Map {

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
        int col = (int) (x / CreateMap.tileSize);
        int row = (int) (y / CreateMap.tileSize);

        if (row < 0 || col < 0 ||
            row >= tiles.length ||
            col >= tiles[0].length) {
            return TileType.WALL.getId();
        }

        return tiles[row][col];
    }
}
