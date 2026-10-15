package game.classes.graphical_user_inteface;

import java.util.HashSet;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainMenu {

    public void drawMainMenu(Pane root, Runnable onPlay) {
        HashSet<Button> buttonSet = new HashSet<Button>();

        Button button1 = new Button("Play");
        Button button2 = new Button("Exit");

        buttonSet.add(button1);
        buttonSet.add(button2);

        VBox menu = new VBox(10);

        
        menu.setAlignment(Pos.CENTER);
        menu.prefWidthProperty().bind(root.widthProperty());
        menu.prefHeightProperty().bind(root.heightProperty());

        button1.setOnAction(e -> {
            root.getChildren().remove(menu);
            button1.fire();
            e.consume();
        });

        button2.setOnAction(e -> {
            Platform.exit();
        });

        for (Button b : buttonSet) {
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
