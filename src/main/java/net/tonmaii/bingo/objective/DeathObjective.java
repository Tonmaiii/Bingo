package net.tonmaii.bingo.objective;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.DeathCallback;

public class DeathObjective extends BaseObjective {

    void callback(PlayerEntity player, DamageSource source) {
        complete(player);
    }

    public DeathObjective() {
        name = "Die";
        DeathCallback.EVENT.register((player, source) -> {
            if (isEnabled()) {
                callback(player, source);
            }
            return ActionResult.PASS;
        });
    }
}
