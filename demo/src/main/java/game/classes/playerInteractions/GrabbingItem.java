package game.classes.playerInteractions;

import game.classes.entities.Item;
import game.classes.entities.Player;
import game.classes.itemsInteractions.ItemsInteraction;
import game.classes.movement.Collision;
import javafx.animation.AnimationTimer;

public class GrabbingItem {

    Collision cols = new Collision();
    ItemsInteraction itemsInteraction = new ItemsInteraction();

    public void GrabbingItem(Player player, Item item) {

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (cols.CollidingHitbox(player, item)) {
                    itemsInteraction.getItemType(item, player);
                    item.kill();
                    stop();
                }
            }
        }.start();
    }
}
