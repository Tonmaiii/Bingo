package net.tonmaii.bingo.objective;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KillByEntityObjective extends DeathObjective {

    EntityType<? extends LivingEntity> entity;

    @Override
    void callback(PlayerEntity player, DamageSource source) {
        if (
            source.getAttacker() != null &&
            entity.equals(source.getAttacker().getType())
        ) {
            complete(player);
        }
    }

    public KillByEntityObjective(
        EntityType<? extends LivingEntity> entity,
        Item icon
    ) {
        super();
        name = "Die to " + entity.getName().getString();
        this.icon = new ItemStack(icon);
        this.entity = entity;
    }
}
