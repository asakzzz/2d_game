package game.classes.graphical_user_inteface;

import java.util.HashSet;

import game.App;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainMenu {

    public void drawMainMenu(Pane root, Runnable onPlay) {

        Image sprite1 = new Image(App.class.getResourceAsStream("assets/buttons/play/play.png"));
        ImageView iw1 = new ImageView(sprite1);
        Image sprite2 = new Image(App.class.getResourceAsStream("assets/buttons/exit/exit.png"));
        ImageView iw2 = new ImageView(sprite2);

        HashSet<Button> buttonSet = new HashSet<Button>();

        Button button1 = new Button();
        button1.setGraphic(iw1);
        button1.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        button1.setScaleX(0.9);
        button1.setScaleY(0.9);
        
        Button button2 = new Button();
        button2.setGraphic(iw2);
        button2.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        button2.setScaleX(0.7);
        button2.setScaleY(0.7);



        buttonSet.add(button1);
        buttonSet.add(button2);

        VBox menu = new VBox(10);

        menu.setAlignment(Pos.CENTER);
        menu.prefWidthProperty().bind(root.widthProperty());
        menu.prefHeightProperty().bind(root.heightProperty());

        button1.setOnAction(e -> {
            root.getChildren().remove(menu);
            onPlay.run();
            e.consume();
        });

        button2.setOnAction(e -> {
            Platform.exit();
        });

        for (Button b : buttonSet) {
            b.setOpacity(0.6);
            b.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                b.setOpacity(isFocused ? 1.0 : 0.6);
            });
            b.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    onPlay.run();
                }
            });
        }

        menu.getChildren().addAll(button1, button2);

        root.getChildren().add(menu);

    }

}
