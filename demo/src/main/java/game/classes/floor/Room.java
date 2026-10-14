package game.classes.floor;

import java.util.EnumSet;
import java.util.Set;

import game.classes.maps.Map;

public class Room {

    private final int gridX;
    private final int gridY;
    private final Map map;
    private final Set<Direction> doors = EnumSet.noneOf(Direction.class);
 
    public Room(int gridX, int gridY, Map map) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.map = map;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public Map getMap() {
        return map;
    }

    public void addDoor(Direction direction) {
        doors.add(direction);
    }

    public boolean hasDoor(Direction direction) {
        return doors.contains(direction);
    }

    public Set<Direction> getDoors() {
        return EnumSet.copyOf(doors);
    }
}