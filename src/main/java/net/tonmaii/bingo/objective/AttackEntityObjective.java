package net.tonmaii.bingo.objective;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class AttackEntityObjective extends BaseObjective {

    void callback(
        PlayerEntity player,
        World world,
        Hand hand,
        Entity entity,
        EntityHitResult hitResult
    ) {
        complete(player);
    }

    public AttackEntityObjective() {
        name = "Attack an entity";
        AttackEntityCallback.EVENT.register(
            (player, world, hand, entity, hitResult) -> {
                if (isEnabled()) {
                    callback(player, world, hand, entity, hitResult);
                }
                return ActionResult.PASS;
            }
        );
    }
}
