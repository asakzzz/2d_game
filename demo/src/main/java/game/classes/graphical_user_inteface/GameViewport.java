package game.classes.graphical_user_inteface;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Keeps the game at its real size (the "world") and scales it to fit the window,
 * centred, with black bars if the window ratio is different.
 *
 * Everything in the game (room, player, enemies, projectiles, HUD) goes in
 * {@link #getWorld()} and keeps using normal world coordinates.
 * The Scene must be created with {@link #getRoot()}.
 */
public class GameViewport {

    private final double worldWidth;
    private final double worldHeight;

    private final Pane world = new Pane();
    private final StackPane container = new StackPane();

    public GameViewport(double worldWidth, double worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        world.setMinSize(worldWidth, worldHeight);
        world.setPrefSize(worldWidth, worldHeight);
        world.setMaxSize(worldWidth, worldHeight);
        world.setClip(new Rectangle(worldWidth, worldHeight));   // nothing is drawn outside the room

        // a Group takes the scaled size into account, so the StackPane can centre it
        container.getChildren().add(new Group(world));
        container.setStyle("-fx-background-color: black;");

        container.widthProperty().addListener((obs, o, n) -> updateScale());
        container.heightProperty().addListener((obs, o, n) -> updateScale());
    }

    private void updateScale() {
        double scale = Math.min(container.getWidth() / worldWidth,
                                container.getHeight() / worldHeight);
        if (scale > 0) {
            world.setScaleX(scale);
            world.setScaleY(scale);
        }
    }

    public Pane getWorld() {
        return world;
    }

    public StackPane getRoot() {
        return container;
    }
}