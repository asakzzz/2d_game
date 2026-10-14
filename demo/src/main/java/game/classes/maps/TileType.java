package game.classes.maps;

public enum TileType {

    GROUND(0),
    WALL(1),
    DOOR(2),
    PREVIOUS_DOOR(3);

    private final int id;

    TileType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
