package me.bionicbeanie.mods;

import org.lwjgl.glfw.GLFW;

import me.bionicbeanie.mods.api.ICoordinateListHandler;
import me.bionicbeanie.mods.api.ICoordinateSaveHandler;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IPositionCalculator;
import me.bionicbeanie.mods.api.IScreen;
import me.bionicbeanie.mods.gui.CoordinatesGui;
import me.bionicbeanie.mods.gui.CoordinatesScreen;
import me.bionicbeanie.mods.impl.CoordinateListHandler;
import me.bionicbeanie.mods.impl.CoordinateSaveHandler;
import me.bionicbeanie.mods.impl.FileStore;
import me.bionicbeanie.mods.impl.PositionCalculator;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class SaveCoordinatesClient implements ClientModInitializer {

    private static IPositionCalculator positionCalculator = new PositionCalculator();

    @Override
    public void onInitializeClient() {
       
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.savecoords.coords",
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "category.savecoords.generic"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                PlayerRawPosition rawPosition = positionCalculator.locate(client);
                IFileStore fileStore = new FileStore(client.runDirectory.getAbsolutePath());
                ICoordinateSaveHandler saveHandler = new CoordinateSaveHandler(fileStore);
                ICoordinateListHandler listHandler = new CoordinateListHandler(fileStore);
                IScreen screen = new IScreen() {

                    @Override
                    public void close() {
                        client.openScreen(null);
                    }
                };
                client.openScreen(
                        new CoordinatesScreen(new CoordinatesGui(rawPosition, saveHandler, screen, listHandler)));
            }
        });
    }
}
