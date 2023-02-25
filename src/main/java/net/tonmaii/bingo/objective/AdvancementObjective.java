package net.tonmaii.bingo.objective;

import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.AdvancementCallback;

public class AdvancementObjective extends BaseObjective {

    String advancementId;

    void callback(PlayerEntity player, Advancement advancement) {
        if (advancementId.equals(advancement.getId().getPath())) {
            complete(player);
        }
    }

    public AdvancementObjective(String name, String advancementId, Item icon) {
        this.name = name;
        this.icon = new ItemStack(icon);
        this.advancementId = advancementId;
        AdvancementCallback.EVENT.register((player, advancement) -> {
            if (isEnabled()) {
                callback(player, advancement);
            }
            return ActionResult.PASS;
        });
    }
}
