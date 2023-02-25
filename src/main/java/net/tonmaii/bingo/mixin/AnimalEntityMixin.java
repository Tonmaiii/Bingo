package net.tonmaii.bingo.mixin;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.BreedAnimalCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {

    @Inject(method = "breed", at = @At("HEAD"))
    private void breed(
        ServerWorld world,
        AnimalEntity other,
        CallbackInfo info
    ) {
        AnimalEntity animal = (AnimalEntity) (Object) this;
        ServerPlayerEntity player = animal.getLovingPlayer();
        ActionResult result = BreedAnimalCallback.EVENT
            .invoker()
            .interact(player, animal, other);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
