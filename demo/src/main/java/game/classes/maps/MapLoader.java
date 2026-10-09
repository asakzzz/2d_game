package game.classes.maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapLoader {
    public static Map getMapFile(String MapPath) {
        int[][] map = new int [13][24];
        File file = new File(MapPath);
        int count = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] values = line.split("");
                for (int i = 0; i < values.length; i++) {
                    map[count][i] = Integer.parseInt(values[i]);
                }
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new Map(map);
    }
}