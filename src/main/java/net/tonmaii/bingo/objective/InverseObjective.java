package net.tonmaii.bingo.objective;

import java.util.HashSet;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerManager;

public abstract class InverseObjective extends BaseObjective {

    private HashSet<String> completed = new HashSet<String>();

    @Override
    void complete(PlayerEntity player) {
        completed.add(player.getName().getString());
        PlayerManager playerManager = player.getServer().getPlayerManager();
        if (
            completed.size() < playerManager.getCurrentPlayerCount() - 1
        ) return;

        playerManager
            .getPlayerList()
            .stream()
            .filter(p -> !completed.contains(p.getName().getString()))
            .forEach(p -> super.complete(p));
    }
}
