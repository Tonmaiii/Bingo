package net.tonmaii.bingo.command;

import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.tonmaii.bingo.gui.GuiBoard;

public class BoardCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register(
                (
                    CommandDispatcher<ServerCommandSource> dispatcher,
                    boolean dedicated
                ) ->
            dispatcher.register(literal("board").executes(BoardCommand::board))
        );
    }

    public static int board(CommandContext<ServerCommandSource> context)
        throws CommandSyntaxException {
        GuiBoard.open(context.getSource().getPlayer());

        return 0;
    }
}
