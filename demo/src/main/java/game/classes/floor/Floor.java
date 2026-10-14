package game.classes.floor;

import java.util.Collection;
import java.util.HashMap;

public class Floor {

    /** A position on the room grid (in rooms, not pixels). */
    public record Cell(int x, int y) {
        public Cell move(Direction d) {
            return new Cell(x + d.getDx(), y + d.getDy());
        }
    }

    // java.util.Map written in full because "Map" is your own class
    private final java.util.Map<Cell, Room> rooms = new HashMap<>();
    private Room startRoom;
    private Room bossRoom;

    void addRoom(Room room) {
        rooms.put(new Cell(room.getGridX(), room.getGridY()), room);
    }

    void setStartRoom(Room room) { this.startRoom = room; }
    void setBossRoom(Room room)  { this.bossRoom = room; }

    public Room getStartRoom() { return startRoom; }
    public Room getBossRoom()  { return bossRoom; }
    public Collection<Room> getRooms() { return rooms.values(); }

    public Room getRoom(int gridX, int gridY) {
        return rooms.get(new Cell(gridX, gridY));
    }

    /** The room behind a door of 'room', or null if there is no door that way. */
    public Room getNeighbor(Room room, Direction dir) {
        if (!room.hasDoor(dir)) return null;
        return getRoom(room.getGridX() + dir.getDx(), room.getGridY() + dir.getDy());
    }
}