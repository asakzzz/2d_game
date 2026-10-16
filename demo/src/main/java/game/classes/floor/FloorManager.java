package game.classes.floor;

import game.classes.maps.Map;
import game.classes.maps.MapPool;

public class FloorManager {

    private final FloorGenerator generator = new FloorGenerator();
    private Floor floor;
    private Room currentRoom;
    private int floorNumber;

    public void startFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.floor = generator.generate(floorNumber, new MapPool(floorNumber));
        this.currentRoom = floor.getStartRoom();
    }

    public void nextFloor() {
        startFloor(floorNumber + 1);
    }

    /**
     * Moves through a door. Returns the new room, or null if there is no door that
     * way.
     */
    public Room changeRoom(Direction dir) {
        Room next = floor.getNeighbor(currentRoom, dir);
        if (next != null)
            currentRoom = next;
        return next;
    }

    /** Which door of the current room is at this pixel position? null if none. */
    public Direction getDoorDirection(Map map, double x, double y, double w, double h) {
        int ts = Map.getTileSize();
        int rows = map.getTiles().length;
        int cols = map.getTiles()[0].length;

        int firstCol = (int) Math.floor(x / ts);
        int lastCol = (int) Math.floor((x + w - 0.01) / ts);
        int firstRow = (int) Math.floor(y / ts);
        int lastRow = (int) Math.floor((y + h - 0.01) / ts);

        for (int row = firstRow; row <= lastRow; row++) {
            for (int col = firstCol; col <= lastCol; col++) {
                if (map.isDoor(col * ts + 1, row * ts + 1)) {
                    if (row == 0)
                        return Direction.UP;
                    if (row == rows - 1)
                        return Direction.DOWN;
                    if (col == 0)
                        return Direction.LEFT;
                    if (col == cols - 1)
                        return Direction.RIGHT;
                }
            }
        }
        return null;
    }

    /**
     * Where to place the player after moving in 'movedDir' (arrives on the opposite
     * side).
     */
    public double[] getSpawnPosition(Direction movedDir, double w, double h) {
        int[][] t = getCurrentMap().getTiles();
        int rows = t.length;
        int cols = t[0].length;
        int ts = Map.getTileSize();

        double inTileX = (ts - w) / 2; 
        double inTileY = (ts - h) / 2;
        double midX = cols * ts / 2.0 - w / 2;
        double midY = (rows / 2) * ts + inTileY;

        return switch (movedDir) {
            case UP -> new double[] { midX, (rows - 2) * ts + inTileY };
            case DOWN -> new double[] { midX, ts + inTileY };
            case LEFT -> new double[] { (cols - 2) * ts + inTileX, midY };
            case RIGHT -> new double[] { ts + inTileX, midY };
        };
    }

    public double[] getCenter() {
        int[][] t = getCurrentMap().getTiles();
        int ts = Map.getTileSize();
        return new double[] { t[0].length * ts / 2.0, t.length * ts / 2.0 };
    }

    public Floor getFloor() {
        return floor;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Map getCurrentMap() {
        return currentRoom.getMap();
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}