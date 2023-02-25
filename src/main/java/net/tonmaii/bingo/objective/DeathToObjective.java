package net.tonmaii.bingo.objective;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DeathToObjective extends DeathObjective {

    DamageSource source;

    @Override
    public void callback(PlayerEntity player, DamageSource source) {
        if (this.source.equals(source)) {
            complete(player);
        }
    }

    public DeathToObjective(DamageSource source, Item icon) {
        super();
        name = "Die to " + source.getName();
        this.icon = new ItemStack(icon);
        this.source = source;
    }
}
