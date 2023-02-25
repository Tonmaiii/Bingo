package net.tonmaii.bingo.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface BreedAnimalCallback {
    Event<BreedAnimalCallback> EVENT = EventFactory.createArrayBacked(
        BreedAnimalCallback.class,
        listeners ->
            (player, animal1, animal2) -> {
                for (BreedAnimalCallback listener : listeners) {
                    ActionResult result = listener.interact(
                        player,
                        animal1,
                        animal2
                    );

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            }
    );

    ActionResult interact(
        PlayerEntity player,
        AnimalEntity animal1,
        AnimalEntity animal2
    );
}
