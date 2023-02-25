package net.tonmaii.bingo.objective;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.tonmaii.bingo.event.BreedAnimalCallback;

public class BreedAnimalObjective extends BaseObjective {

    EntityType<? extends AnimalEntity> animal;

    void callback(
        PlayerEntity player,
        AnimalEntity animal1,
        AnimalEntity animal2
    ) {
        if (
            this.animal.equals(animal1.getType()) &&
            this.animal.equals(animal2.getType())
        ) {
            complete(player);
        }
    }

    public BreedAnimalObjective(EntityType<? extends AnimalEntity> animal, Item icon) {
        name = "Breed " + animal.getName().getString();
        this.icon = new ItemStack(icon);
        this.animal = animal;
        BreedAnimalCallback.EVENT.register((player, animal1, animal2) -> {
            if (isEnabled()) {
                callback(player, animal1, animal2);
            }
            return ActionResult.PASS;
        });
    }
}
