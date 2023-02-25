package net.tonmaii.bingo.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ActionResult;

public interface InventoryCallback {
    Event<InventoryCallback> EVENT = EventFactory.createArrayBacked(
        InventoryCallback.class,
        listeners ->
            (player, inventory) -> {
                for (InventoryCallback listener : listeners) {
                    ActionResult result = listener.interact(player, inventory);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, PlayerInventory inventory);
}
