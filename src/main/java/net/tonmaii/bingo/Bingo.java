package net.tonmaii.bingo;

import java.util.ArrayList;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.tonmaii.bingo.command.BoardCommand;
import net.tonmaii.bingo.command.InspectionCommand;
import net.tonmaii.bingo.command.StartCommand;
import net.tonmaii.bingo.objective.AdvancementObjective;
import net.tonmaii.bingo.objective.BaseObjective;
import net.tonmaii.bingo.objective.BreakBlockObjective;
import net.tonmaii.bingo.objective.BreedAnimalObjective;
import net.tonmaii.bingo.objective.DeathToObjective;
import net.tonmaii.bingo.objective.EatFoodObjective;
import net.tonmaii.bingo.objective.EffectObjective;
import net.tonmaii.bingo.objective.InverseDeathObjective;
import net.tonmaii.bingo.objective.InverseObtainItemObjective;
import net.tonmaii.bingo.objective.KillByEntityObjective;
import net.tonmaii.bingo.objective.KillEntityObjective;
import net.tonmaii.bingo.objective.KillOtherPlayerObjective;
import net.tonmaii.bingo.objective.ObtainItemObjective;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bingo implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("bingo");

    public static String[] completion = new String[25];
    public static BaseObjective[] objectives = new BaseObjective[25];
    public static ArrayList<BaseObjective> allObjectives = new ArrayList<BaseObjective>();

    public static int inspectionTime = 20 * 60;

    @Override
    public void onInitialize() {
        StartCommand.register();
        BoardCommand.register();
        InspectionCommand.register();
        addObjectives();
    }

    public static void startGame() {
        completion = new String[25];
        objectives = new BaseObjective[25];
        selectObjectives();
    }

    static void selectObjectives() {
        ArrayList<BaseObjective> copyAllObjectives = new ArrayList<BaseObjective>(
            allObjectives
        );
        for (int i = 0; i < 25; i++) {
            int index = (int) (Math.random() * copyAllObjectives.size());
            objectives[i] = copyAllObjectives.get(index);
            copyAllObjectives.remove(index);
        }
    }

    static void addObjectives() {
        allObjectives.add(new ObtainItemObjective(Items.POWDER_SNOW_BUCKET));
        allObjectives.add(new ObtainItemObjective(Items.COMPASS));
        allObjectives.add(new ObtainItemObjective(Items.ELYTRA));
        allObjectives.add(
            new ObtainItemObjective(
                "full Diamond armor",
                Items.DIAMOND_HELMET,
                Items.DIAMOND_CHESTPLATE,
                Items.DIAMOND_LEGGINGS,
                Items.DIAMOND_BOOTS
            )
        );
        allObjectives.add(
            new ObtainItemObjective(
                "full Diamond tools",
                Items.DIAMOND_AXE,
                Items.DIAMOND_PICKAXE,
                Items.DIAMOND_SHOVEL,
                Items.DIAMOND_SWORD,
                Items.DIAMOND_HOE
            )
        );
        allObjectives.add(
            new ObtainItemObjective(
                "full Gold armor",
                Items.GOLDEN_HELMET,
                Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS,
                Items.GOLDEN_BOOTS
            )
        );
        allObjectives.add(
            new ObtainItemObjective(
                "full Gold tools",
                Items.GOLDEN_AXE,
                Items.GOLDEN_PICKAXE,
                Items.GOLDEN_SHOVEL,
                Items.GOLDEN_SWORD,
                Items.GOLDEN_HOE
            )
        );
        allObjectives.add(
            new ObtainItemObjective(
                "full Wooden tools",
                Items.WOODEN_AXE,
                Items.WOODEN_PICKAXE,
                Items.WOODEN_SHOVEL,
                Items.WOODEN_SWORD,
                Items.WOODEN_HOE
            )
        );
        allObjectives.add(
            new ObtainItemObjective(
                "full Iron armor",
                Items.IRON_HELMET,
                Items.IRON_CHESTPLATE,
                Items.IRON_LEGGINGS,
                Items.IRON_BOOTS
            )
        );
        allObjectives.add(
            new ObtainItemObjective(
                "full Leather armor",
                Items.LEATHER_HELMET,
                Items.LEATHER_CHESTPLATE,
                Items.LEATHER_LEGGINGS,
                Items.LEATHER_BOOTS
            )
        );
        allObjectives.add(new ObtainItemObjective(Items.NAUTILUS_SHELL));
        allObjectives.add(new ObtainItemObjective(Items.SLIME_BLOCK));
        allObjectives.add(new ObtainItemObjective(Items.DISPENSER));
        allObjectives.add(new ObtainItemObjective(Items.LIGHTNING_ROD));
        allObjectives.add(new ObtainItemObjective(Items.END_ROD));
        allObjectives.add(new ObtainItemObjective(Items.END_CRYSTAL));
        allObjectives.add(new ObtainItemObjective(Items.AMETHYST_SHARD));
        allObjectives.add(new ObtainItemObjective(Items.HEART_OF_THE_SEA));

        allObjectives.add(new InverseObtainItemObjective(Items.WHEAT_SEEDS));
        allObjectives.add(new InverseObtainItemObjective(Items.NETHERRACK));
        allObjectives.add(new InverseObtainItemObjective(Items.CRAFTING_TABLE));

        allObjectives.add(
            new BreakBlockObjective(Blocks.DEEPSLATE_DIAMOND_ORE)
        );
        allObjectives.add(new BreakBlockObjective(Blocks.EMERALD_ORE));
        allObjectives.add(new BreakBlockObjective(Blocks.SPAWNER));
        allObjectives.add(new BreakBlockObjective(Blocks.OBSIDIAN));

        allObjectives.add(new EatFoodObjective(Items.ENCHANTED_GOLDEN_APPLE));
        allObjectives.add(new EatFoodObjective(Items.CHORUS_FRUIT));
        allObjectives.add(new EatFoodObjective(Items.PUFFERFISH));
        allObjectives.add(new EatFoodObjective(Items.TROPICAL_FISH));

        allObjectives.add(
            new BreedAnimalObjective(EntityType.CHICKEN, Items.WHEAT_SEEDS)
        );
        allObjectives.add(
            new BreedAnimalObjective(EntityType.HORSE, Items.HAY_BLOCK)
        );
        allObjectives.add(
            new BreedAnimalObjective(EntityType.DONKEY, Items.HAY_BLOCK)
        );
        allObjectives.add(
            new BreedAnimalObjective(EntityType.WOLF, Items.BONE)
        );
        allObjectives.add(
            new BreedAnimalObjective(EntityType.TURTLE, Items.SEAGRASS)
        );
        allObjectives.add(
            new BreedAnimalObjective(EntityType.HOGLIN, Items.CRIMSON_FUNGUS)
        );
        allObjectives.add(
            new BreedAnimalObjective(EntityType.BEE, Items.DANDELION)
        );

        allObjectives.add(
            new KillEntityObjective(EntityType.ELDER_GUARDIAN, Items.SPONGE)
        );
        allObjectives.add(
            new KillEntityObjective(EntityType.GHAST, Items.GHAST_TEAR)
        );
        allObjectives.add(
            new KillEntityObjective(EntityType.IRON_GOLEM, Items.IRON_INGOT)
        );
        allObjectives.add(
            new KillEntityObjective(EntityType.PIGLIN_BRUTE, Items.GOLDEN_AXE)
        );
        allObjectives.add(
            new KillEntityObjective(EntityType.PILLAGER, Items.CROSSBOW)
        );
        allObjectives.add(new KillOtherPlayerObjective());
        allObjectives.add(
            new KillEntityObjective(EntityType.WITCH, Items.GLASS_BOTTLE)
        );
        allObjectives.add(
            new KillEntityObjective(EntityType.ZOGLIN, Items.ROTTEN_FLESH)
        );

        allObjectives.add(
            new AdvancementObjective(
                "Find a Nether Fortress",
                "nether/find_fortress",
                Items.NETHER_BRICKS
            )
        );
        allObjectives.add(
            new AdvancementObjective(
                "Find a Bastion Remnant",
                "nether/find_bastion",
                Items.POLISHED_BLACKSTONE_BRICKS
            )
        );
        allObjectives.add(
            new AdvancementObjective(
                "Find a Stronghold",
                "story/follow_ender_eye",
                Items.MOSSY_STONE_BRICKS
            )
        );
        allObjectives.add(
            new AdvancementObjective(
                "Find an End City",
                "end/find_end_city",
                Items.PURPUR_BLOCK
            )
        );

        allObjectives.add(
            new EffectObjective(StatusEffects.GLOWING, Items.SPECTRAL_ARROW)
        );
        allObjectives.add(
            new EffectObjective(StatusEffects.MINING_FATIGUE, Items.PRISMARINE)
        );
        allObjectives.add(
            new EffectObjective(StatusEffects.JUMP_BOOST, Items.RABBIT_FOOT)
        );
        allObjectives.add(
            new EffectObjective(StatusEffects.SPEED, Items.SUGAR)
        );
        allObjectives.add(
            new EffectObjective(StatusEffects.BAD_OMEN, Items.SOUL_SAND)
        );
        allObjectives.add(
            new EffectObjective(StatusEffects.DOLPHINS_GRACE, Items.COD)
        );
        allObjectives.add(
            new EffectObjective(StatusEffects.WATER_BREATHING, Items.PUFFERFISH)
        );

        allObjectives.add(new InverseDeathObjective());

        allObjectives.add(
            new KillByEntityObjective(EntityType.BEE, Items.HONEYCOMB)
        );
        allObjectives.add(
            new DeathToObjective(
                DamageSource.OUT_OF_WORLD,
                Items.BLACK_CONCRETE
            )
        );
        allObjectives.add(
            new DeathToObjective(DamageSource.CRAMMING, Items.MINECART)
        );
        allObjectives.add(
            new DeathToObjective(DamageSource.IN_WALL, Items.SAND)
        );
        allObjectives.add(
            new DeathToObjective(DamageSource.ANVIL, Items.ANVIL)
        );
    }
}
