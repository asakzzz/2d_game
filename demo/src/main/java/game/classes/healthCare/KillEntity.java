package game.classes.healthCare;

import game.classes.entities.Enemy;
import game.classes.entities.Player;
import javafx.scene.layout.Pane;

public class KillEntity<T> {

   public void killPlayer (Player player , Pane root) {
    if (player.getHp() <= 0 && player.isAlive()) {
        player.kill();
    }
   }

   public void killEnemy(Enemy enemy , Pane root) {
    if (enemy.getHp() <= 0 && enemy.isAlive()) {
        enemy.kill();
    }
   }
}
