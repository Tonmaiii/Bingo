package net.tonmaii.bingo.objective;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.EffectCallback;

public class EffectObjective extends BaseObjective {

    StatusEffect effect;

    void callback(PlayerEntity player, StatusEffectInstance effect) {
        if (this.effect.equals(effect.getEffectType())) {
            complete(player);
        }
    }

    public EffectObjective(StatusEffect effect, Item icon) {
        name = "Get the " + effect.getName().getString() + " effect";
        this.icon = new ItemStack(icon);
        this.effect = effect;
        EffectCallback.EVENT.register((player, statusEffect) -> {
            if (isEnabled()) {
                callback(player, statusEffect);
            }
            return ActionResult.PASS;
        });
    }
}
