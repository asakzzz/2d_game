package game.classes.graphical_user_inteface;

import java.util.HashSet;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class GameOverMenu {

    public void GameOver(Pane root , Runnable onPlay) {

        HashSet<Button> buttonSet = new HashSet<Button>();

        Button button1 = new Button( " Retry");
        button1.setScaleX(0.9);
        button1.setScaleY(0.9);

        Button button2 = new Button( "Embrace cowardice");
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
            e.consume();
            onPlay.run();

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
                    b.fire();
                    e.consume();
                }
            });
        }

        menu.getChildren().addAll(button1, button2);
        button1.requestFocus();

        root.getChildren().add(menu);
    }

}
