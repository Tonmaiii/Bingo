package net.tonmaii.bingo.objective;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class KillOtherPlayerObjective extends KillObjective {

    @Override
    void callback(PlayerEntity player, Entity entity, DamageSource source) {
        if (entity instanceof PlayerEntity && !entity.equals(player)) {
            complete(player);
        }
    }

    public KillOtherPlayerObjective() {
        super();
        name = "Kill another player";
        icon = new ItemStack(Items.DIAMOND_SWORD);
    }
}
