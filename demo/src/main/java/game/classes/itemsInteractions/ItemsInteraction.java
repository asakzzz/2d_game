package game.classes.itemsInteractions;

import game.classes.entities.Item;
import game.classes.entities.Player;;
public class ItemsInteraction {

    public void HealingItem(Player player) {
        player.increaseHp(1);
    }

    public void OffensiveItem(Player player) {

        player.increaseAttackDamage(1);

    }

    public void getItemType(Item item , Player player) {
        int ItemType = item.getEffect();

        switch (ItemType) {
            case 0:
                OffensiveItem(player);
                break;
            case 1:
                HealingItem(player);
                break;
        }
    }
    
}
