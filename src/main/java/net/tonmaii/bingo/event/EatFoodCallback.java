package net.tonmaii.bingo.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;

public interface EatFoodCallback {
    Event<EatFoodCallback> EVENT = EventFactory.createArrayBacked(
        EatFoodCallback.class,
        listeners ->
            (player, food) -> {
                for (EatFoodCallback listener : listeners) {
                    ActionResult result = listener.interact(player, food);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, Item food);
}
