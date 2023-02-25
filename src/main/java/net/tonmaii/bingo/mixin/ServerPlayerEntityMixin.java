package net.tonmaii.bingo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.DeathCallback;
import net.tonmaii.bingo.event.EffectCallback;
import net.tonmaii.bingo.event.InventoryCallback;
import net.tonmaii.bingo.event.KillEntityCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "updateKilledAdvancementCriterion", at = @At("INVOKE"))
    private void onKilledOther(
        Entity other,
        int score,
        DamageSource damageSource,
        CallbackInfo info
    ) {
        if (!(other instanceof LivingEntity)) return;
        ActionResult result = KillEntityCallback.EVENT
            .invoker()
            .interact(
                (PlayerEntity) (Object) this,
                (LivingEntity) other,
                damageSource
            );

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }

    @Inject(method = "playerTick", at = @At("TAIL"))
    private void update(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ActionResult result = InventoryCallback.EVENT
            .invoker()
            .interact(player, player.getInventory());

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource source, CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ActionResult result = DeathCallback.EVENT
            .invoker()
            .interact(player, source);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }

    @Inject(method = "onStatusEffectApplied", at = @At("HEAD"))
    private void onStatusEffectApplied(
        StatusEffectInstance effect,
        Entity source,
        CallbackInfo info
    ) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        ActionResult result = EffectCallback.EVENT
            .invoker()
            .interact(player, effect);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
