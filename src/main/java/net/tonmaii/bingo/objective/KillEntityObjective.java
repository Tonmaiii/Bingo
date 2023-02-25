package net.tonmaii.bingo.objective;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KillEntityObjective extends KillObjective {

    EntityType<? extends LivingEntity> entityType;

    @Override
    void callback(PlayerEntity player, Entity entity, DamageSource source) {
        if (entityType.equals(entity.getType())) {
            complete(player);
        }
    }

    public KillEntityObjective(
        EntityType<? extends LivingEntity> entityType,
        Item icon
    ) {
        super();
        name = "Kill " + entityType.getName().getString();
        this.icon = new ItemStack(icon);
        this.entityType = entityType;
    }
}
