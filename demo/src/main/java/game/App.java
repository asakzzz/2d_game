package game;

import java.io.IOException;
import java.util.ArrayList;

import game.classes.enemyShooting.EnemyShooting;
import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import game.classes.floor.Direction;
import game.classes.floor.FloorGenerator;
import game.classes.floor.FloorManager;
import game.classes.graphical_user_inteface.MainMenu;
import game.classes.healthCare.DealDamage;
import game.classes.healthCare.KillEntity;
import game.classes.graphical_user_inteface.GameViewport;
import game.classes.maps.Map;
import game.classes.maps.MapRenderer;
import game.classes.movement.enemyMovements.EnemyNearestNeighbor;
import game.classes.movement.enemyMovements.EnemyRandomMovement;
import game.classes.movement.playerMovements.MovementControlPlayer;
import game.classes.playerInteractions.ProjectileShooting;
import game.classes.sprites.ImageReading;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App<T> extends Application {

    private static Scene scene;
    private Pane root;

    @Override
    public void start(Stage stage) throws IOException {
        GameViewport viewport = new GameViewport(
                FloorGenerator.COLS * Map.getTileSize(),
                FloorGenerator.ROWS * Map.getTileSize());
        this.root = viewport.getWorld(); // everything else keeps adding to root
        scene = new Scene(viewport.getRoot(), 1280, 720);

    }

    private void startGame(Stage stage) throws IOException {
        ArrayList<Projectile> playerProjectile = new ArrayList<Projectile>();
        ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
        System.out.println("startGame called");
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
        Player player = new Player(5, 0, 0, sprite, root);

        Rectangle playerHitbox = player.getHitbox();

        // --- Damage ---
        DealDamage dealDamage = new DealDamage();
        KillEntity killEntity = new KillEntity<T>();

        playerHitbox.setFill(javafx.scene.paint.Color.TRANSPARENT);
        playerHitbox.setStroke(javafx.scene.paint.Color.GREEN);
        playerHitbox.setStrokeWidth(2);

        root.getChildren().add(playerHitbox);

        MovementControlPlayer movement = implementPlayer(player, activeMap);
        ProjectileShooting projectileShooting = new ProjectileShooting();
        projectileShooting.SpawnDefaultProjectile(root, scene, player, movement, activeMap, playerProjectile);  //find a way to add map to this mf
        Player player = new Player(100, 0, 0, sprite);
        ProjectileShooting projectileShooting = new ProjectileShooting();
        MovementControlPlayer movement = implementPlayer(player, activeMap);
        placeEntity(player, floorManager.getCenter(), 0, 0);

        // --- enemy ---
        Enemy enemy = new Enemy(3, 0, 0, sprite, root);
        EnemyNearestNeighbor enemyMovement = new EnemyNearestNeighbor();
        EnemyRandomMovement enemyRandomMovement = new EnemyRandomMovement();
        EnemyShooting enemyShooting = new EnemyShooting();
        ImageView enemyView = enemy.getImageView();
        enemyView.setFitWidth(100);
        enemyView.setPreserveRatio(true);
        root.getChildren().add(enemyView);
        placeEntity(enemy, floorManager.getCenter(), 200, 0);

        // --- debug ---
        Rectangle playerBox = new Rectangle(player.getWidth(), player.getHeight());
        playerBox.setFill(Color.TRANSPARENT);
        playerBox.setStroke(Color.RED);
        playerBox.setStrokeWidth(2);
        root.getChildren().add(playerBox);

        // --- HUD (added last so it draws on top) ---
        Text speedText = new Text();
        speedText.setX(10);
        speedText.setY(20);
        root.getChildren().add(speedText);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        new AnimationTimer() {
            private boolean canChangeRoom = true;

            @Override
            public void handle(long now) {

                if (!player.isAlive()) {
                    stop();
                    return;
                }
                speedText.setText(String.format("Speed = %.1f", movement.getSpeed()));
                projectileShooting.SpawnDefaultProjectile(root, scene, player, movement, activeMap); // find a way to
                                                                                                     // add map to this
                                                                                                     // mf

                // enemy first, before any early return below
                // enemyMovement.enemyNearestNeighbor(enemy, player, activeMap);
                if (enemy.isAlive()) {
                    enemyRandomMovement.enemyRandomMovement(enemy, player, activeMap, now);
                    enemyShooting.enemyShooting(enemy, root, player, movement, activeMap, sprite, enemyProjectiles);
                }

                dealDamage.dealDamage(player, enemy, playerProjectile, enemyProjectiles, root);

                Direction door = floorManager.getDoorDirection(activeMap, player.getX_pos(), player.getY_pos(),
                        player.getWidth(), player.getHeight());
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
                    placePlayer(player, floorManager.getSpawnPosition(door, player.getWidth(), player.getHeight()));
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
        view.setFitWidth(50);
        view.setPreserveRatio(true);
        root.getChildren().add(view);
        return inputControl;
    }

    private void placePlayer(Player player, double[] pos) {
        player.setX_pos(pos[0]);
        player.setY_pos(pos[1]);
    }

    private void placeEntity(game.classes.entities.Entity e, double[] pos, double offsetX, double offsetY) {
        e.setX_pos(pos[0] + offsetX);
        e.setY_pos(pos[1] + offsetY);
    }

    public static void main(String[] args) {
        launch();
    }
}
