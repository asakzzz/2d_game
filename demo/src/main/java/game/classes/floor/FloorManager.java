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

    /** Moves through a door. Returns the new room, or null if there is no door that way. */
    public Room changeRoom(Direction dir) {
        Room next = floor.getNeighbor(currentRoom, dir);
        if (next != null) currentRoom = next;
        return next;
    }

    /** Which door of the current room is at this pixel position? null if none. */
    public Direction getDoorAt(double x, double y) {
        Map map = getCurrentMap();
        if (!map.isDoor(x, y)) return null;

        int ts = Map.getTileSize();
        int col = (int) (x / ts);
        int row = (int) (y / ts);
        int rows = map.getTiles().length;
        int cols = map.getTiles()[0].length;

        if (row == 0)        return Direction.UP;
        if (row == rows - 1) return Direction.DOWN;
        if (col == 0)        return Direction.LEFT;
        if (col == cols - 1) return Direction.RIGHT;
        return null;
    }

    /** Where to place the player after moving in 'movedDir' (arrives on the opposite side). */
    public double[] getSpawnPosition(Direction movedDir) {
        int[][] t = getCurrentMap().getTiles();
        int rows = t.length;
        int cols = t[0].length;
        int ts = Map.getTileSize();

        return switch (movedDir) {
            case UP    -> new double[]{cols * ts / 2.0, (rows - 2) * ts};
            case DOWN  -> new double[]{cols * ts / 2.0, ts};
            case LEFT  -> new double[]{(cols - 2) * ts, rows * ts / 2.0};
            case RIGHT -> new double[]{ts, rows * ts / 2.0};
        };
    }

    public double[] getCenter() {
        int[][] t = getCurrentMap().getTiles();
        int ts = Map.getTileSize();
        return new double[]{t[0].length * ts / 2.0, t.length * ts / 2.0};
    }

    public Floor getFloor()      { return floor; }
    public Room getCurrentRoom() { return currentRoom; }
    public Map getCurrentMap()   { return currentRoom.getMap(); }
    public int getFloorNumber()  { return floorNumber; }
}