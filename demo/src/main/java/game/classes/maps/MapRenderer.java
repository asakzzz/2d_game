package game.classes.maps;

import game.classes.sprites.ImageReading;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MapRenderer {
    private static final int SHEET_TILE_SIZE = 24;
    private static final String SHEET_PATH = "/game/assets/tiles/P_P_FREE_RPG_TILESET/Dungeon_24x24.png";
    private static final int[][] SHEET_POSITIONS = {
            { 13, 0 }, // GROUND
            { 3, 1 }, // WALL
            { 2, 0 }, // DOOR
            { 3, 0 } // PREVIOUS_DOOR
    };
    private static final int[] CORNER_POSITION = { 5, 0 };
    private static Image[] tileImages;
    private static Image cornerImage;

    static public Canvas render(Map map) {
        loadSprites();

        int ts = Map.getTileSize();
        int[][] tiles = map.getTiles();
        int rows = tiles.length;
        int cols = tiles[0].length;

        Canvas canvas = new Canvas(cols * ts, rows * ts);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tile = tiles[row][col];

                if (tile == TileType.WALL.getId() && cornerImage != null && isCorner(row, col, rows, cols)) {
                    drawTile(gc, cornerImage, col * ts, row * ts, ts, getCornerAngle(row, col, rows, cols));
                    continue;
                }

                Image img = getImage(tile);

                if (img != null) {
                    double angle = needsRotation(tile) ? getEdgeAngle(row, col, rows, cols) : 0;
                    drawTile(gc, img, col * ts, row * ts, ts, angle);
                } else {
                    gc.setFill(getColor(tile));
                    gc.fillRect(col * ts, row * ts, ts, ts);
                }
            }
        }
        return canvas;
    }

    private static boolean needsRotation(int tile) {
        return tile == TileType.WALL.getId()
                || tile == TileType.DOOR.getId()
                || tile == TileType.PREVIOUS_DOOR.getId();
    }

    private static double getEdgeAngle(int row, int col, int rows, int cols) {
        if (row == 0)
            return 0;
        if (row == rows - 1)
            return 180;
        if (col == cols - 1)
            return 90;
        if (col == 0)
            return 270;
        return 0;
    }

    private static boolean isCorner(int row, int col, int rows, int cols) {
        return (row == 0 || row == rows - 1) && (col == 0 || col == cols - 1);
    }

    /** Assumes the corner sprite is drawn as a TOP-LEFT corner. */
    private static double getCornerAngle(int row, int col, int rows, int cols) {
        if (row == 0 && col == 0)
            return 0;
        if (row == 0 && col == cols - 1)
            return 90;
        if (row == rows - 1 && col == cols - 1)
            return 180;
        return 270;
    }

    /** Draws a tile rotated around its own centre. */
    private static void drawTile(GraphicsContext gc, Image img, double x, double y, double size, double angle) {
        gc.save();
        gc.translate(x + size / 2, y + size / 2);
        gc.rotate(angle);
        gc.drawImage(img, -size / 2, -size / 2, size, size);
        gc.restore();
    }

    private static void loadSprites() {
        if (tileImages != null)
            return;

        tileImages = new Image[SHEET_POSITIONS.length];
        var stream = MapRenderer.class.getResourceAsStream(SHEET_PATH);
        if (stream == null) {
            System.out.println("Tile sheet not found: " + SHEET_PATH + " (using colours)");
            return;
        }

        Image sheet = new Image(stream);
        cornerImage = ImageReading.getFrame(
                sheet, CORNER_POSITION[0], CORNER_POSITION[1],
                SHEET_TILE_SIZE, SHEET_TILE_SIZE);
        for (int id = 0; id < SHEET_POSITIONS.length; id++) {
            tileImages[id] = ImageReading.getFrame(
                    sheet,
                    SHEET_POSITIONS[id][0], SHEET_POSITIONS[id][1],
                    SHEET_TILE_SIZE, SHEET_TILE_SIZE);
        }
    }

    private static Image getImage(int tile) {
        if (tile < 0 || tile >= tileImages.length)
            return null;
        return tileImages[tile];
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