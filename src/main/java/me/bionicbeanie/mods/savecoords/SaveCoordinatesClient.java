package me.bionicbeanie.mods.savecoords;

import me.bionicbeanie.mods.savecoords.gui.impl.ModDI;
import me.bionicbeanie.mods.savecoords.impl.Factory;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SaveCoordinatesClient implements ClientModInitializer {

    private static KeyBinding configKeyBinding;
    private static KeyBinding beamKeyBinding;

    @Override
    public void onInitializeClient() {
        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.savecoords.config",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.savecoords.name"
        ));

        beamKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.savecoords.beam",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                "category.savecoords.name"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeyBinding.wasPressed()) {
                ModDI.getModGui().open();
            }

            while (beamKeyBinding.wasPressed()) {
                IPlayerLocator playerLocator = Factory.CreatePlayerLocator(client);

                if (client.player != null) {
                    client.player.sendChatMessage(playerLocator.locate().toString());
                }
            }
        });

    }
}
