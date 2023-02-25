package net.tonmaii.bingo.objective;

import java.util.Arrays;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.tonmaii.bingo.Bingo;
import net.tonmaii.bingo.gui.GuiBoard;

public abstract class BaseObjective {

    public String name;
    public ItemStack icon;

    void complete(PlayerEntity player) {
        int slot = Arrays.asList(Bingo.objectives).indexOf(this);
        String playerName = player.getName().getString();
        if (Bingo.completion[slot] == null) {
            Bingo.completion[slot] = playerName;
            Bingo.LOGGER.info(
                "{} completed objective {}: {}",
                playerName,
                slot,
                name
            );
            player
                .getServer()
                .getPlayerManager()
                .getPlayerList()
                .forEach(p -> {
                    p.sendMessage(
                        Text.of(
                            String.format(
                                "%s completed objective %s: %s",
                                playerName,
                                slot,
                                name
                            )
                        ),
                        false
                    );
                    GuiBoard.createBoard((ServerPlayerEntity) p);

                    if (p.equals(player)) {
                        p.networkHandler.sendPacket(
                            new PlaySoundIdS2CPacket(
                                SoundEvents.ENTITY_PLAYER_LEVELUP.getId(),
                                p.getSoundCategory(),
                                p.getPos(),
                                1,
                                1
                            )
                        );
                    } else {
                        p.networkHandler.sendPacket(
                            new PlaySoundIdS2CPacket(
                                SoundEvents.ENTITY_GUARDIAN_DEATH.getId(),
                                p.getSoundCategory(),
                                p.getPos(),
                                1,
                                1
                            )
                        );
                    }
                });
        }
    }

    Boolean isEnabled() {
        return Arrays.asList(Bingo.objectives).contains(this);
    }
}
