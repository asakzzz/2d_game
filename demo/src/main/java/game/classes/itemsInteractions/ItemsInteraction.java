package game.classes.itemsInteractions;

import game.classes.entities.Item;
import game.classes.entities.Player;;

public class ItemsInteraction {

    public void HealingItem(Player player) {
        player.increaseHp(1);
        System.out.println(player.getHp());
    }

    public void AttackDamageItem(Player player) {

        player.increaseAttackDamage(1);
        System.out.println(player.getAttackDamage());

    }

    public void AttackSpeedItem(Player player) {
        player.decreseAttackSpeed(50_000_000L);
        System.out.println(player.getAttackCooldown());


    }

    public void getItemType(Item item, Player player) {
        int ItemType = item.getEffect();

        switch (ItemType) {
            case 0:
                AttackDamageItem(player);
                break;
            case 1:
                HealingItem(player);
                break;
            case 2:
                AttackSpeedItem(player);
                break;
        }
    }

}
