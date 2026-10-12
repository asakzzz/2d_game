package game.classes.maps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 *
 * The MapPool class contains a set of map in a Hashmap, meant to be picked
 * randomly and displayed upon entering a door ingame
 */
public class MapPool {

    private final Random r = new Random();
    private int currentId = 0;
    private final Map<Integer, game.classes.maps.Map> maps = new HashMap<>();

    public MapPool() {
        getMapForFloor(1);
    }

    public int getCurrentId() {
        return currentId;
    }

    public final Map<Integer, game.classes.maps.Map> getMapForFloor (int floorNumber) {
        maps.clear();
        File directory = new File(String.format("/src/main/resources/game/maps/floor%d", floorNumber));
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Map directory not found: " + directory.getAbsolutePath());
        }
        File []files = directory.listFiles();
        if (files == null) return null;
        for (File file: files) {
            if (!file.isFile()) continue;
            maps.put(maps.size(), MapLoader.getMapFile(file.getAbsolutePath()));
        }
        return maps;
    }

    /**
     *
     * @param id The id of the map we want to return
     * @return The map
     */
    public game.classes.maps.Map getMap(int id) {
        game.classes.maps.Map selectedMap = maps.get(id);
        if (selectedMap == null) {
            return null;
        }
        if (id == -1) {
            return null;
        }
        currentId = id;
        return selectedMap;
    }

    /**
     *
     * @return A random map located in the HashMap
     */
    public game.classes.maps.Map getRandomMap() {
        List<Integer> ids = new ArrayList<>(maps.keySet());
        int id = ids.get(r.nextInt(ids.size()));
        game.classes.maps.Map selectedMap = maps.get(id);
        if (selectedMap == null) {
            return null;
        }
        if (id == -1) {
            return null;
        }
        return selectedMap;
    }

}
