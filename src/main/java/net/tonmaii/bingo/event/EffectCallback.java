package net.tonmaii.bingo.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface EffectCallback {
    Event<EffectCallback> EVENT = EventFactory.createArrayBacked(
        EffectCallback.class,
        listeners ->
            (player, effect) -> {
                for (EffectCallback listener : listeners) {
                    ActionResult result = listener.interact(player, effect);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, StatusEffectInstance effect);
}
