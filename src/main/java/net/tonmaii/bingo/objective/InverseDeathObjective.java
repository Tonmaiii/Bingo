package net.tonmaii.bingo.objective;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.DeathCallback;

public class InverseDeathObjective extends InverseObjective {

    void callback(PlayerEntity player, DamageSource source) {
        complete(player);
    }

    public InverseDeathObjective() {
        name = "Do not die";
        this.icon = new ItemStack(Items.TOTEM_OF_UNDYING);
        DeathCallback.EVENT.register((player, source) -> {
            if (isEnabled()) {
                callback(player, source);
            }
            return ActionResult.PASS;
        });
    }
}
