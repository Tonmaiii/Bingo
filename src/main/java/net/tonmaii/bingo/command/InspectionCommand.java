package net.tonmaii.bingo.command;

import static com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.tonmaii.bingo.Bingo;

public class InspectionCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register(
                (
                    CommandDispatcher<ServerCommandSource> dispatcher,
                    boolean dedicated
                ) ->
            dispatcher.register(
                literal("inspection")
                    .then(
                        argument("seconds", doubleArg(0))
                            .executes(InspectionCommand::setInspectionTime)
                    )
            )
        );
    }

    public static int setInspectionTime(
        CommandContext<ServerCommandSource> context
    ) throws CommandSyntaxException {
        double seconds = context.getArgument("seconds", double.class);
        Bingo.inspectionTime = (int) (seconds * 20);
        return 0;
    }
}
