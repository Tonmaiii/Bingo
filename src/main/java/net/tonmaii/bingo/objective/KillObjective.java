package net.tonmaii.bingo.objective;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.KillEntityCallback;

public class KillObjective extends BaseObjective {

    void callback(PlayerEntity player, Entity entity, DamageSource source) {
        complete(player);
    }

    public KillObjective() {
        name = "Kill an entity";
        KillEntityCallback.EVENT.register((player, entity, source) -> {
            if (isEnabled()) {
                callback(player, entity, source);
            }
            return ActionResult.PASS;
        });
    }
}
