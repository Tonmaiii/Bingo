package net.tonmaii.bingo.objective;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.InventoryCallback;

public class ObtainItemObjective extends BaseObjective {

    Item[] items;

    void callback(PlayerEntity player, PlayerInventory inventory) {
        for (Item item : items) {
            if (!inventory.contains(item.getDefaultStack())) {
                return;
            }
        }

        complete(player);
    }

    public ObtainItemObjective(Item item) {
        this(item.getName().getString(), new Item[] { item });
    }

    public ObtainItemObjective(String name, Item... items) {
        this.name = "Obtain " + name;
        this.items = items;
        this.icon = new ItemStack(items[0]);
        InventoryCallback.EVENT.register((player, inventory) -> {
            if (isEnabled()) {
                callback(player, inventory);
            }
            return ActionResult.PASS;
        });
    }
}
