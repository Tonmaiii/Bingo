package net.tonmaii.bingo.mixin;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.AdvancementCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {

    @Shadow
    private ServerPlayerEntity owner;

    @Inject(method = "grantCriterion", at = @At("TAIL"))
    private void load(
        Advancement advancement,
        String criterionName,
        CallbackInfoReturnable<Boolean> info
    ) {
        AdvancementProgress advancementProgress =
            ((PlayerAdvancementTracker) (Object) this).getProgress(advancement);
        if (advancementProgress.isDone()) {
            ActionResult result = AdvancementCallback.EVENT
                .invoker()
                .interact(owner, advancement);

            if (result == ActionResult.FAIL) {
                info.cancel();
            }
        }
    }
}
