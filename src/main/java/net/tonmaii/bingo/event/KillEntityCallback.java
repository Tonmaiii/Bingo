package net.tonmaii.bingo.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface KillEntityCallback {
    Event<KillEntityCallback> EVENT = EventFactory.createArrayBacked(
        KillEntityCallback.class,
        listeners ->
            (player, entity, source) -> {
                for (KillEntityCallback listener : listeners) {
                    ActionResult result = listener.interact(
                        player,
                        entity,
                        source
                    );

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(
        PlayerEntity player,
        LivingEntity entity,
        DamageSource source
    );
}
