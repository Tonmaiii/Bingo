package net.tonmaii.bingo.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.tonmaii.bingo.event.EatFoodCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "eatFood", at = @At("INVOKE"))
    private void eatFood(
        World world,
        ItemStack stack,
        CallbackInfoReturnable<ItemStack> info
    ) {
        ActionResult result = EatFoodCallback.EVENT
            .invoker()
            .interact((PlayerEntity) (Object) this, stack.getItem());

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
