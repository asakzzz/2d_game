// package game.classes.maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapLoader {
    public static void main(String[] args) {
        File map = new File("/home/brand/2d_game/demo/src/main/resources/game/maps/floor1/map1.txt");

        try (Scanner myScanner = new Scanner(map)) {
            while (myScanner.hasNextLine()) {
                String data = myScanner.nextLine();
                for (char tile : data.toCharArray()) {
                    System.out.print(tile);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}