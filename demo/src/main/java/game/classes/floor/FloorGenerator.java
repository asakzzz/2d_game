package game.classes.floor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import game.classes.floor.Floor.Cell;
import game.classes.maps.Map;
import game.classes.maps.MapPool;
import game.classes.maps.TileType;

public class FloorGenerator {

    // must match your map files
    public static final int ROWS = 13;
    public static final int COLS = 22;
    private static final int MAX_ROOMS = 15;

    private final Random random = new Random();

    public Floor generate(int floorNumber, MapPool pool) {
        // 1. layout only: which cells have a room, and which doors they have
        java.util.Map<Cell, Set<Direction>> layout = generateLayout(floorNumber);
        Cell start = new Cell(0, 0);
        Cell boss = pickBossCell(layout, start);

        // 2. turn the layout into real rooms
        Floor floor = new Floor();
        for (java.util.Map.Entry<Cell, Set<Direction>> e : layout.entrySet()) {
            Cell cell = e.getKey();
            Set<Direction> doors = e.getValue();

            Map base = cell.equals(start) ? emptyRoom() : pool.getRandomMap();
            Room room = new Room(cell.x(), cell.y(), stampDoors(base, doors));
            for (Direction d : doors) room.addDoor(d);

            floor.addRoom(room);
            if (cell.equals(start)) floor.setStartRoom(room);
            if (cell.equals(boss))  floor.setBossRoom(room);
        }
        return floor;
    }

    // ---------- layout ----------

    private java.util.Map<Cell, Set<Direction>> generateLayout(int floorNumber) {
        int target = Math.min(MAX_ROOMS, 10 + floorNumber * 2 + random.nextInt(2));

        java.util.Map<Cell, Set<Direction>> layout = new LinkedHashMap<>();
        layout.put(new Cell(0, 0), EnumSet.noneOf(Direction.class));

        int attempts = 0;
        while (layout.size() < target && attempts++ < 2000) {
            List<Cell> existing = new ArrayList<>(layout.keySet());
            Cell from = existing.get(random.nextInt(existing.size()));
            Direction dir = Direction.values()[random.nextInt(4)];
            Cell to = from.move(dir);

            if (layout.containsKey(to)) continue;
            if (countNeighbors(layout, to) > 1) continue;   // avoids loops

            layout.put(to, EnumSet.noneOf(Direction.class));
            layout.get(from).add(dir);
            layout.get(to).add(dir.opposite());
        }
        return layout;
    }

    private int countNeighbors(java.util.Map<Cell, Set<Direction>> layout, Cell cell) {
        int n = 0;
        for (Direction d : Direction.values()) {
            if (layout.containsKey(cell.move(d))) n++;
        }
        return n;
    }

    /** Boss = the dead end farthest from the start (BFS). */
    private Cell pickBossCell(java.util.Map<Cell, Set<Direction>> layout, Cell start) {
        java.util.Map<Cell, Integer> dist = new HashMap<>();
        Deque<Cell> queue = new ArrayDeque<>();
        dist.put(start, 0);
        queue.add(start);
        while (!queue.isEmpty()) {
            Cell cur = queue.poll();
            for (Direction d : layout.get(cur)) {
                Cell next = cur.move(d);
                if (!dist.containsKey(next)) {
                    dist.put(next, dist.get(cur) + 1);
                    queue.add(next);
                }
            }
        }

        Cell best = start;
        int bestDist = -1;
        for (java.util.Map.Entry<Cell, Integer> e : dist.entrySet()) {
            boolean deadEnd = layout.get(e.getKey()).size() == 1;
            if (!e.getKey().equals(start) && deadEnd && e.getValue() > bestDist) {
                best = e.getKey();
                bestDist = e.getValue();
            }
        }
        return best;
    }

    // ---------- tiles ----------

    private Map stampDoors(Map base, Set<Direction> doors) {
        int[][] src = base.getTiles();
        int[][] tiles = new int[src.length][];
        for (int i = 0; i < src.length; i++) tiles[i] = src[i].clone();   // copy, maps are shared

        for (Direction d : Direction.values()) {
            int tile = doors.contains(d) ? TileType.DOOR.getId() : TileType.WALL.getId();
            for (int[] cell : doorCells(d, tiles.length, tiles[0].length)) {
                tiles[cell[0]][cell[1]] = tile;
            }
        }
        return new Map(tiles);
    }

    /** {row, col} cells of the door slot on each side. */
    private int[][] doorCells(Direction d, int rows, int cols) {
        int midRow = rows / 2;
        int midCol = cols / 2;
        return switch (d) {
            case UP    -> new int[][]{{0, midCol - 1}, {0, midCol}};
            case DOWN  -> new int[][]{{rows - 1, midCol - 1}, {rows - 1, midCol}};
            case LEFT  -> new int[][]{{midRow, 0}};
            case RIGHT -> new int[][]{{midRow, cols - 1}};
        };
    }

    private Map emptyRoom() {
        int[][] tiles = new int[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                boolean border = r == 0 || c == 0 || r == ROWS - 1 || c == COLS - 1;
                tiles[r][c] = border ? TileType.WALL.getId() : TileType.GROUND.getId();
            }
        }
        return new Map(tiles);
    }
}