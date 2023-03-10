package net.tonmaii.bingo.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface AdvancementCallback {
    Event<AdvancementCallback> EVENT = EventFactory.createArrayBacked(
        AdvancementCallback.class,
        listeners ->
            (player, advancement) -> {
                for (AdvancementCallback listener : listeners) {
                    ActionResult result = listener.interact(
                        player,
                        advancement
                    );

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, Advancement advancement);
}
