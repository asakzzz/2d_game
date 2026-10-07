package game;

import java.io.IOException;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import game.classes.maps.CreateMap;
import game.classes.maps.MapPool;
import game.classes.movement.Collision;
import game.classes.movement.MovementControlPlayer;
import game.classes.sprites.AnimateSprite;
import game.classes.sprites.ImageReading;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App<T> extends Application {

    private static Scene scene;
    private Pane root;

    @Override
    public void start(Stage stage) throws IOException {
        this.root = new Pane();
        scene = new Scene(root, 1280, 720);
        CreateMap map = new CreateMap();
        MapPool pool = new MapPool();
        Image sheet = new Image(App.class.getResourceAsStream("assets/knight.png"));
        Image sprite = ImageReading.getFrame(sheet, 0, 0, 32, 32);
        Player player = new Player(100, 0, 0, sprite);
        Enemy enemy = new Enemy(10, 0, 0, sprite);

        //initialize player in another file
        root.getChildren().add(map.CreateCanva());
        MovementControlPlayer mvnt = implementPlayer(player, map);
        Collision collision = new Collision<T>();

        AnimateSprite animation = new AnimateSprite(8);
        ImageReading imageReading = new ImageReading();
        Image[] framesRunning = imageReading.getAnimation(sheet, 0, 2, 8, 32, 32);
        Image[] framesIdle = imageReading.getAnimation(sheet, 0, 0, 4, 32, 32);

        Text text = new Text();
        Text text2 = new Text();
        Text text3 = new Text();

        text.setX(1700);
        text.setY(50);
        root.getChildren().add(text);

        text2.setX(1700);
        text2.setY(80);
        root.getChildren().add(text2);

        text3.setX(1700);
        text3.setY(110);
        root.getChildren().add(text3);

        //temporary
        Rectangle playerHitbox = player.getHitbox();
        Rectangle enemyHitbox = enemy.getHitbox();

        playerHitbox.setFill(javafx.scene.paint.Color.TRANSPARENT);
        playerHitbox.setStroke(javafx.scene.paint.Color.GREEN);
        playerHitbox.setStrokeWidth(2);

        enemyHitbox.setFill(javafx.scene.paint.Color.TRANSPARENT);
        enemyHitbox.setStroke(javafx.scene.paint.Color.GREEN);
        enemyHitbox.setStrokeWidth(2);

// Add the hitbox visual to the scene root
        root.getChildren().add(playerHitbox);
        root.getChildren().add(enemyHitbox);

        stage.setScene(scene);

        stage.show();

        new AnimationTimer() {
            private boolean changed = false;

            @Override
            public void handle(long now) {
                double pos_x = player.getX_pos();
                double pos_y = player.getY_pos();
                double width = player.getWidth();
                double height = player.getHeight();
                player.getHitbox();
                enemy.getHitbox();

                text.setText(String.format("Speed = %.1f", mvnt.getSpeed()));
                text2.setText(String.format("isMoving = " + mvnt.isMoving()));
                text3.setText(String.format("isColliding = " + collision.CollidingHitbox(player, enemy)));

                boolean playerOnDoor = map.isDoor(pos_x, pos_y, width, height);
                boolean playerOnPreviousDoor = map.isPreviousDoor(pos_x, pos_y, width, height);

                //can make a file outta this
                if (mvnt.isMoving()) {
                    animation.animate(player, framesRunning, now);
                } else {
                    animation.animate(player, framesIdle, now);
                }

                //can make another file outta this
                if (playerOnDoor == false && playerOnPreviousDoor == false) {
                    changed = true;
                    return;
                }
                if (changed == false) {
                    return;
                }

                if (playerOnDoor) {
                    changed = false;
                    map.loadTIles(pool.getRandomMap());
                    root.getChildren().set(0, map.CreateCanva());
                } else if (playerOnPreviousDoor && pool.getPreviousId() != -1) {
                    changed = false;
                    map.loadTIles(pool.goBack());
                    root.getChildren().set(0, map.CreateCanva());
                }

            }
        }.start();
    }

    //Put implementPlayer in another file
    public MovementControlPlayer implementPlayer(Player player, CreateMap map) throws IOException {
        MovementControlPlayer inputControl = new MovementControlPlayer(player, scene, map);
        inputControl.handleInput();
        ImageView view = player.getImageView();
        view.setFitWidth(64);
        view.setPreserveRatio(true);
        root.getChildren().add(player.getImageView());
        return inputControl;
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
