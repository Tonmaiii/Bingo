package net.tonmaii.bingo.gui;

import eu.pb4.sgui.api.gui.SimpleGui;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.IntStream;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Text.Serializer;
import net.tonmaii.bingo.Bingo;

public class GuiBoard {

    private static HashMap<String, SimpleGui> boards = new HashMap<>();

    private static final int width = 9;
    private static final int height = 5;

    public static void open(ServerPlayerEntity player) {
        createBoard(player);
        SimpleGui gui = boards.get(player.getName().getString());
        gui.open();
    }

    public static void close(ServerPlayerEntity player) {
        SimpleGui gui = boards.get(player.getName().getString());
        gui.close();
    }

    public static void openAll(MinecraftServer server) {
        server
            .getPlayerManager()
            .getPlayerList()
            .forEach(player -> {
                GuiBoard.open(player);
            });
    }

    public static void closeAll(MinecraftServer server) {
        server
            .getPlayerManager()
            .getPlayerList()
            .forEach(player -> {
                GuiBoard.close(player);
            });
    }

    public static boolean isOpen(ServerPlayerEntity player) {
        SimpleGui gui = boards.get(player.getName().getString());
        if (gui == null) return false;
        return gui.isOpen();
    }

    public static void createBoard(ServerPlayerEntity player) {
        SimpleGui gui = new SimpleGui(
            ScreenHandlerType.GENERIC_9X5,
            player,
            false
        );
        gui.setTitle(Text.of("Bingo Board"));
        boards.put(player.getName().getString(), gui);
        updateBoard(player);
        gui.onClose();
    }

    private static void updateBoard(ServerPlayerEntity player) {
        SimpleGui gui = boards.get(player.getName().getString());
        if (gui == null) return;
        HashMap<String, Integer> completion = new HashMap<>();

        for (int i = 0; i < 25; i++) {
            int x = i % 5;
            int y = i / 5;

            ItemStack icon;

            if (player.getName().getString().equals(Bingo.completion[i])) {
                icon = new ItemStack(Items.LIME_STAINED_GLASS_PANE);
            } else if (Bingo.completion[i] != null) {
                icon = new ItemStack(Items.RED_STAINED_GLASS_PANE);
            } else if (Bingo.objectives[i].icon != null) {
                icon = Bingo.objectives[i].icon;
            } else icon = new ItemStack(Items.BARRIER);

            gui.setSlot(
                (height / 2 - y + 2) * width + (width / 2 - x + 2),
                (icon.setCustomName(Text.of(Bingo.objectives[i].name)))
            );

            if (Bingo.completion[i] != null) {
                if (completion.get(Bingo.completion[i]) == null) {
                    completion.put(Bingo.completion[i], 1);
                } else {
                    completion.put(
                        Bingo.completion[i],
                        completion.get(Bingo.completion[i]) + 1
                    );
                }
            }
        }

        List<Entry<String, Integer>> sortedCompletions = completion
            .entrySet()
            .stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .toList();

        IntStream
            .range(0, sortedCompletions.size())
            .forEach(i -> {
                ItemStack icon = new ItemStack(Items.PLAYER_HEAD);
                icon.setCount(sortedCompletions.get(i).getValue());
                NbtCompound nbt = icon.getOrCreateNbt();
                nbt.putString("SkullOwner", sortedCompletions.get(i).getKey());
                NbtCompound display = icon.getOrCreateSubNbt("display");
                display.putString(
                    "Name",
                    Serializer.toJson(
                        Text.of(sortedCompletions.get(i).getKey())
                    )
                );

                gui.setSlot(i * width, icon);
            });
    }
}
