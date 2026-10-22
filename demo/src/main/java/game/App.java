package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import game.classes.enemyShooting.EnemyShooting;
import game.classes.entities.Enemy;
import game.classes.entities.Item;
import game.classes.entities.Player;
import game.classes.entities.Projectile;
import game.classes.floor.Direction;
import game.classes.floor.FloorGenerator;
import game.classes.floor.FloorManager;
import game.classes.graphical_user_inteface.GameOverMenu;
import game.classes.graphical_user_inteface.GameViewport;
import game.classes.graphical_user_inteface.MainMenu;
import game.classes.healthCare.DealDamage;
import game.classes.healthCare.KillEntity;
import game.classes.maps.Map;
import game.classes.maps.MapRenderer;
import game.classes.movement.enemyMovements.EnemyRandomMovement;
import game.classes.movement.playerMovements.MovementControlPlayer;
import game.classes.playerInteractions.GrabbingItem;
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
    private MainMenu mainMenu = new MainMenu();
    private GameOverMenu gameOver = new GameOverMenu();

    @Override
    public void start(Stage stage) throws IOException {
        this.root = new Pane();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();

        mainMenu.drawMainMenu(root, () -> {
            try {
                startGame(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void startGame(Stage stage) throws IOException {

        ArrayList<Projectile> playerProjectile = new ArrayList<Projectile>();
        ArrayList<Projectile> enemyProjectiles = new ArrayList<Projectile>();
        System.out.println("startGame called");

        GameViewport viewport = new GameViewport(
                FloorGenerator.COLS * Map.getTileSize(),
                FloorGenerator.ROWS * Map.getTileSize());
        this.root = viewport.getWorld(); // everything else keeps adding to root
        scene = new Scene(viewport.getRoot(), 1280, 720);

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

        MovementControlPlayer movement = implementPlayer(player, activeMap);
        placeEntity(player, floorManager.getCenter(), 0, 0);
        ProjectileShooting projectileShooting = new ProjectileShooting();
        projectileShooting.SpawnDefaultProjectile(root, scene, player, movement, activeMap, playerProjectile); // find a
                                                                                                               // way to

        DealDamage dealDamage = new DealDamage();
        KillEntity killEntity = new KillEntity();

        // --- enemy ---
        Enemy enemy = new Enemy(3, 0, 0, sprite, root);
        EnemyRandomMovement enemyRandomMovement = new EnemyRandomMovement();
        EnemyShooting enemyShooting = new EnemyShooting();
        ImageView enemyView = enemy.getImageView();
        enemyView.setFitWidth(100);
        enemyView.setPreserveRatio(true);
        root.getChildren().add(enemyView);
        Random r = new Random();
        double r1 = r.nextDouble(100);
        double r2 = r.nextDouble(100);
        placeEntity(enemy, floorManager.getCenter(), r1, r2);
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);

        // --- item ---
        Image Itemsheet = new Image(App.class.getResourceAsStream("assets/mugs.png"));
        Image Itemsprite = ImageReading.getFrame(Itemsheet, 1, 3, 32, 32);
        Item healCup = new Item(1, Itemsprite, root); // 0 attackDamage 1 health 2 attackSpeed
        ImageView itemView = healCup.getImageView();
        GrabbingItem grabbingItem = new GrabbingItem();
        grabbingItem.GrabbingItem(player, healCup);

        // --- item ---
        Image Itemsheet2 = new Image(App.class.getResourceAsStream("assets/mugs.png"));
        Image Itemsprite2 = ImageReading.getFrame(Itemsheet2, 1, 4, 32, 32);
        Item damageCup = new Item(0, Itemsprite2, root); // 0 attackDamage 1 health 2 attackSpeed
        ImageView itemView2 = damageCup.getImageView();
        grabbingItem.GrabbingItem(player, damageCup);

        // --- item ---
        Image Itemsheet3 = new Image(App.class.getResourceAsStream("assets/mugs.png"));
        Image Itemsprite3 = ImageReading.getFrame(Itemsheet3, 1, 5, 32, 32);
        Item cooldownCup = new Item(2, Itemsprite3, root); // 0 attackDamage 1 health 2 attackSpeed
        ImageView itemView3 = cooldownCup.getImageView();
        grabbingItem.GrabbingItem(player, damageCup);
        grabbingItem.GrabbingItem(player, healCup);
        grabbingItem.GrabbingItem(player, cooldownCup);

        itemView.setFitWidth(50);
        itemView.setPreserveRatio(true);
        root.getChildren().add(itemView);
        itemView2.setFitWidth(50);
        itemView2.setPreserveRatio(true);
        root.getChildren().add(itemView2);
        itemView3.setFitWidth(50);
        itemView3.setPreserveRatio(true);
        root.getChildren().add(itemView3);
        placeEntity(healCup, floorManager.getCenter(), 100, 100);
        placeEntity(cooldownCup, floorManager.getCenter(), 300, 100);
        placeEntity(damageCup, floorManager.getCenter(), 500, 100);

        // --- debug ---
        Rectangle playerBox = new Rectangle(player.getWidth(), player.getHeight());
        playerBox.setFill(Color.TRANSPARENT);
        playerBox.setStroke(Color.RED);
        playerBox.setStrokeWidth(2);
        root.getChildren().add(playerBox);

        Rectangle enemyBox = new Rectangle(enemy.getWidth(), enemy.getHeight());
        enemyBox.setFill(Color.TRANSPARENT);
        enemyBox.setStroke(Color.RED);
        enemyBox.setStrokeWidth(2);
        root.getChildren().add(enemyBox);

        Rectangle itemBox = new Rectangle(healCup.getWidth(), healCup.getHeight());
        itemBox.setFill(Color.TRANSPARENT);
        itemBox.setStroke(Color.RED);
        itemBox.setStrokeWidth(2);
        root.getChildren().add(itemBox);

        // --- HUD (added last so it draws on top) ---
        Text speedText = new Text();
        speedText.setX(10);
        speedText.setY(20);
        root.getChildren().add(speedText);

        Text lives = new Text();
        lives.setX(10);
        lives.setY(50);
        root.getChildren().add(lives);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        new AnimationTimer() {
            private boolean canChangeRoom = true;

            @Override
            public void handle(long now) {

                if (!player.isAlive()) {
                    stop();
                    gameOver.GameOver(root, () -> {
                        try {
                            startGame(stage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                playerBox.setWidth(player.getWidth() - 20);
                playerBox.setHeight(player.getHeight() - 15);
                playerBox.setX(player.getX_pos() + 10);
                playerBox.setY(player.getY_pos() + 9);

                speedText.setText(String.format("Speed = %.1f", movement.getSpeed()));
                lives.setText(String.format("lives = " + player.getHp()));

                Iterator<Enemy> it = enemies.iterator();
                while (it.hasNext()) {
                    Enemy e = it.next();
                    if (!e.isAlive()) {
                        it.remove();
                        continue;
                    }
                    enemyBox.setWidth(e.getWidth() - 20);
                    enemyBox.setHeight(e.getHeight() - 15);
                    enemyBox.setX(e.getX_pos() + 10);
                    enemyBox.setY(e.getY_pos() + 9);
                    enemyRandomMovement.enemyRandomMovement(e, player, activeMap, now);
                    enemyShooting.enemyShooting(e, root, player, movement, activeMap, sprite, enemyProjectiles);
                    dealDamage.dealDamage(player, e, playerProjectile, enemyProjectiles, root);
                    killEntity.killEnemy(e, root);
                }

                killEntity.killPlayer(player, root);

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

                    for (Enemy old : enemies)
                        old.kill();
                    enemies.clear();

                    Enemy newEnemy = new Enemy(5, 0, 0, sprite, root);
                    newEnemy.getImageView().setFitWidth(100);
                    newEnemy.getImageView().setPreserveRatio(true);
                    root.getChildren().add(newEnemy.getImageView());
                    placeEntity(newEnemy, floorManager.getCenter(), r1, r2);
                    enemies.add(newEnemy);
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
