package game;

import java.io.IOException;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.floor.Direction;
import game.classes.floor.FloorGenerator;
import game.classes.floor.FloorManager;
import game.classes.maps.Map;
import game.classes.maps.MapRenderer;
import game.classes.movement.enemyMovements.EnemyNearestNeighbor;
import game.classes.movement.playerMovements.MovementControlPlayer;
import game.classes.playerInteractions.ProjectileShooting;
import game.classes.sprites.ImageReading;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private Pane root;

    @Override
    public void start(Stage stage) throws IOException {
        this.root = new Pane();
        scene = new Scene(root, 1280, 720);

        // --- floor ---
        FloorManager floorManager = new FloorManager();
        floorManager.startFloor(1);

        // --- active map (the player's controller keeps this one object forever) ---
        Map activeMap = new Map(new int[FloorGenerator.ROWS][FloorGenerator.COLS]);
        copyTiles(floorManager.getCurrentMap(), activeMap);

        // room is always child index 0
        root.getChildren().add(MapRenderer.render(activeMap));

        // --- sprites ---
        Image sheet = new Image(App.class.getResourceAsStream("assets/knight.png"));
        Image sprite = ImageReading.getFrame(sheet, 0, 0, 32, 32);

        // --- player ---
        Player player = new Player(100, 0, 0, sprite);
        ProjectileShooting projectileShooting =  new ProjectileShooting();
        MovementControlPlayer movement = implementPlayer(player, activeMap);
        placeEntity(player, floorManager.getCenter(), 0, 0);

        // --- enemy ---
        Enemy enemy = new Enemy(10, 0, 0, sprite);
        EnemyNearestNeighbor enemyMovement = new EnemyNearestNeighbor();
        ImageView enemyView = enemy.getImageView();
        enemyView.setFitWidth(100);
        enemyView.setPreserveRatio(true);
        root.getChildren().add(enemyView);
        placeEntity(enemy, floorManager.getCenter(), 200, 0);  

        // --- HUD (added last so it draws on top) ---
        Text speedText = new Text();
        speedText.setX(10);
        speedText.setY(20);
        root.getChildren().add(speedText);

        stage.setScene(scene);
        stage.show();

        new AnimationTimer() {
            private boolean canChangeRoom = true;

            @Override
            public void handle(long now) {
                speedText.setText(String.format("Speed = %.1f", movement.getSpeed()));
                // projectileShooting.SpawnDefaultProjectile(root, scene, player, movement, map);  //find a way to add map to this mf


                // enemy first, before any early return below
                enemyMovement.enemyNearestNeighbor(enemy, player, activeMap);

                Direction door = floorManager.getDoorAt(player.getX_pos(), player.getY_pos());
                if (door == null) {
                    canChangeRoom = true;
                    return;
                }
                if (!canChangeRoom) {
                    return;
                }

                if (floorManager.changeRoom(door) != null) {
                    canChangeRoom = false;
                    copyTiles(floorManager.getCurrentMap(), activeMap);
                    root.getChildren().set(0, MapRenderer.render(activeMap));
                    placePlayer(player, floorManager.getSpawnPosition(door));
                    placeEntity(enemy, floorManager.getCenter(), 0, 0);
                }
            }
        }.start();
    }

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
        root.getChildren().add(view);
        return inputControl;
    }

    private void placePlayer(Player player, double[] pos) {
        player.setX_pos(pos[0]);     // setters take double now, no (int) cast needed
        player.setY_pos(pos[1]);
    }

    private void placeEntity(game.classes.entities.Entity e, double[] pos, double offsetX, double offsetY) {
        e.setX_pos(pos[0] + offsetX);
        e.setY_pos(pos[1] + offsetY);
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
