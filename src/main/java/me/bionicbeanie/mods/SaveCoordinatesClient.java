package me.bionicbeanie.mods;

import org.lwjgl.glfw.GLFW;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.bionicbeanie.mods.core.ICoordinateSaveHandler;
import me.bionicbeanie.mods.core.IFileStore;
import me.bionicbeanie.mods.core.IPositionCalculator;
import me.bionicbeanie.mods.gui.CoordinatesScreen;
import me.bionicbeanie.mods.gui.PlayerPositionGuiDescription;
import me.bionicbeanie.mods.impl.CoordinateSaveHandler;
import me.bionicbeanie.mods.impl.FileStore;
import me.bionicbeanie.mods.impl.PositionCalculator;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class SaveCoordinatesClient implements ClientModInitializer {

    private static IPositionCalculator positionCalculator = new PositionCalculator();

    @Override
    public void onInitializeClient() {

        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("coords").then(ClientCommandManager
                .argument("desc", StringArgumentType.string()).executes(SaveCoordinatesClient::printCoordinates)));

        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.savecoords.coords",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.savecoords.generic"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                PlayerRawPosition rawPosition = positionCalculator.locate(client.player);
                IFileStore fileStore = new FileStore(client.runDirectory.getAbsolutePath());
                ICoordinateSaveHandler saveHandler = new CoordinateSaveHandler(fileStore);
                client.openScreen(new CoordinatesScreen(new PlayerPositionGuiDescription(rawPosition, saveHandler)));
            }
        });
    }

    private static int printCoordinates(CommandContext<FabricClientCommandSource> context)
            throws CommandSyntaxException {
        FabricClientCommandSource source = context.getSource();
        PlayerRawPosition position = positionCalculator.locate(source.getPlayer());

        Text text = new LiteralText("<Coordinates> " + position.getWorldDimension() + " at " + " [ " + position.getX()
                + " , " + position.getY() + " , " + position.getZ() + " ]");

        source.getPlayer().sendMessage(text, false);

        return 1;
    }
}
