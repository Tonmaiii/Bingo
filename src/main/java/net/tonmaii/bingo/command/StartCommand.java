package net.tonmaii.bingo.command;

import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.tonmaii.bingo.Bingo;
import net.tonmaii.bingo.gui.GuiBoard;

public class StartCommand {

    private static int inspectionTicks;
    private static MinecraftServer server;

    public static void register() {
        CommandRegistrationCallback.EVENT.register(
                (
                    CommandDispatcher<ServerCommandSource> dispatcher,
                    boolean dedicated
                ) ->
            dispatcher.register(literal("start").executes(StartCommand::start))
        );
    }

    public static int start(CommandContext<ServerCommandSource> context)
        throws CommandSyntaxException {
        Bingo.startGame();
        server = context.getSource().getServer();
        broadcast(
            "§eGame will begin in " + Bingo.inspectionTime / 20 + " seconds."
        );
        GuiBoard.openAll(server);
        inspectionTicks = Bingo.inspectionTime;
        return 0;
    }

    public static void tick() {
        if (inspectionTicks <= 0) return;
        inspectionTicks--;

        server
            .getPlayerManager()
            .getPlayerList()
            .forEach(player -> {
                if (!GuiBoard.isOpen(player)) GuiBoard.open(player);
            });

        if (inspectionTicks == 20 * 30) broadcast("§cStarting in 30 seconds");
        if (inspectionTicks == 20 * 10) broadcast("§cStarting in 10 seconds");
        if (inspectionTicks == 20 * 5) broadcast("§cStarting in 5");
        if (inspectionTicks == 20 * 4) broadcast("§cStarting in 4");
        if (inspectionTicks == 20 * 3) broadcast("§cStarting in 3");
        if (inspectionTicks == 20 * 2) broadcast("§6Starting in 2");
        if (inspectionTicks == 20 * 1) broadcast("§eStarting in 1");
        if (inspectionTicks == 20 * 0) {
            broadcast("§aGo!!");
            broadcast("§edo /board to reopen the board");
            GuiBoard.closeAll(server);
        }
    }

    private static void broadcast(String message) {
        server
            .getPlayerManager()
            .getPlayerList()
            .forEach(player -> {
                player.sendMessage(Text.of(message), false);
                player.sendMessage(Text.of(message), true);
            });
    }
}
