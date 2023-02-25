package net.tonmaii.bingo.objective;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.InventoryCallback;

public class InverseObtainItemObjective extends InverseObjective {

    Item item;

    void callback(PlayerEntity player, PlayerInventory inventory) {
        if (!inventory.contains(item.getDefaultStack())) return;
        complete(player);
    }

    public InverseObtainItemObjective(Item item) {
        name = "Do not obtain " + item.getName().getString();
        this.item = item;
        icon = new ItemStack(item);
        InventoryCallback.EVENT.register((player, inventory) -> {
            if (isEnabled()) {
                callback(player, inventory);
            }
            return ActionResult.PASS;
        });
    }
}
