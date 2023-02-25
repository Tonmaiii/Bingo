package net.tonmaii.bingo.objective;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.EatFoodCallback;

public class EatFoodObjective extends BaseObjective {

    Item food;

    void callback(PlayerEntity player, Item food) {
        if (this.food.equals(food)) {
            complete(player);
        }
    }

    public EatFoodObjective(Item food) {
        name = "Eat " + food.getName().getString();
        this.food = food;
        this.icon = new ItemStack(food);
        EatFoodCallback.EVENT.register((player, eatenFood) -> {
            if (isEnabled()) {
                callback(player, eatenFood);
            }
            return ActionResult.PASS;
        });
    }
}
