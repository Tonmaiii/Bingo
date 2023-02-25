package net.tonmaii.bingo.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface DeathCallback {
    Event<DeathCallback> EVENT = EventFactory.createArrayBacked(
        DeathCallback.class,
        listeners ->
            (player, source) -> {
                for (DeathCallback listener : listeners) {
                    ActionResult result = listener.interact(player, source);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(PlayerEntity player, DamageSource source);
}
