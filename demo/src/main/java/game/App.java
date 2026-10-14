package game;

import java.io.IOException;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.maps.CreateMap;
import game.classes.maps.MapPool;
import game.classes.movement.Collision;
import game.classes.movement.enemyMovements.EnemyFleePlayer;
import game.classes.movement.enemyMovements.EnemyNearestNeighbor;
import game.classes.movement.enemyMovements.EnemyRandomMovement;
import game.classes.movement.playerMovements.MovementControlPlayer;
import game.classes.movement.projectileMovements.MovementControlProjectile;
import game.classes.playerInteractions.ProjectileShooting;
import game.classes.sprites.AnimateSprite;
import game.classes.sprites.ImageReading;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.star;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App<T> extends Application {
        scene = new Scene(root, 1280, 720);

        // --- floor ---
        FloorManager floorManager = new FloorManager();
        floorManager.startFloor(1);

        // --- active map ---
        // MovementControlPlayer keeps a reference to ONE Map forever, so we give it
        // this one and copy the current room's tiles into it on every room change.
        Map activeMap = new Map(new int[FloorGenerator.ROWS][FloorGenerator.COLS]);
        copyTiles(floorManager.getCurrentMap(), activeMap);

        // room is always child index 0
        root.getChildren().add(MapRenderer.render(activeMap));

        // --- player ---
        Image sheet = new Image(App.class.getResourceAsStream("assets/knight.png"));
        Image sprite = ImageReading.getFrame(sheet, 0, 0, 32, 32);
        Player player = new Player(100, 0, 0, sprite);
        MovementControlPlayer movement = implementPlayer(player, activeMap);
        placePlayer(player, floorManager.getCenter());

        // --- HUD ---
        Text speedText = new Text();
        speedText.setX(10);
        speedText.setY(20);
        root.getChildren().add(speedText);

        stage.setScene(scene);
        stage.show();

        new AnimationTimer() {
            private boolean canChangeRoom = true;   // false until the player leaves the door tile

            @Override
            public void handle(long now) {
                speedText.setText(String.format("Speed = %.1f", movement.getSpeed()));

                Direction door = floorManager.getDoorAt(player.getX_pos(), player.getY_pos());
                if (door == null) {
                    canChangeRoom = true;
                    return;
                }
                if (!canChangeRoom) return;

                if (floorManager.changeRoom(door) != null) {
                    canChangeRoom = false;
                    copyTiles(floorManager.getCurrentMap(), activeMap);
                    root.getChildren().set(0, MapRenderer.render(activeMap));
                    placePlayer(player, floorManager.getSpawnPosition(door));
                }
            }
        }.start();
    }

    /** Copies the tiles of one map into another (same dimensions). */
    private void copyTiles(Map from, Map to) {
        int[][] src = from.getTiles();
        int[][] dst = to.getTiles();
        for (int row = 0; row < src.length; row++) {
            System.arraycopy(src[row], 0, dst[row], 0, src[row].length);
        }
    }

    private MovementControlPlayer implementPlayer(Player player, Map map) throws IOException {
        MovementControlPlayer inputControl = new MovementControlPlayer(player, scene, map);
        inputControl.handleInput();
        ImageView view = player.getImageView();
        view.setFitWidth(100);
        view.setPreserveRatio(true);
        root.getChildren().add(player.getImageView());
        return inputControl;
    }

    private void placePlayer(Player player, double[] pos) {
        player.setX_pos((int)pos[0]);
        player.setY_pos((int)pos[1]);
    }

    public static void main(String[] args) {
        launch();
    }
}