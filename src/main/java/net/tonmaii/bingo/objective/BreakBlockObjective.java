package net.tonmaii.bingo.objective;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BreakBlockObjective extends BaseObjective {

    Block block;

    void callback(
        World world,
        PlayerEntity player,
        BlockPos pos,
        BlockState blockState,
        BlockEntity entity
    ) {
        if (block.equals(blockState.getBlock())) {
            complete(player);
        }
    }

    public BreakBlockObjective(Block block) {
        name = "Break " + block.getName().getString();
        this.block = block;
        this.icon = new ItemStack(block);
        PlayerBlockBreakEvents.AFTER.register(
            (world, player, pos, state, entity) -> {
                if (isEnabled()) {
                    callback(world, player, pos, state, entity);
                }
            }
        );
    }
}
