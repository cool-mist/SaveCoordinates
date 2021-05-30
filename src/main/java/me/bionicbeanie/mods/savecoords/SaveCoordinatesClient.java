package me.bionicbeanie.mods.savecoords;

import org.lwjgl.glfw.GLFW;

import me.bionicbeanie.mods.savecoords.gui.impl.ModGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class SaveCoordinatesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        KeyBinding keyBinding = registerKeyBinding();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                ModGui.start(client);
            }
        });
    }

    private KeyBinding registerKeyBinding() {
        String translationKey = "key.savecoords.coords";
        String category = "category.savecoords.generic";
        int keyBind = GLFW.GLFW_KEY_H;

        KeyBinding keyBinding = new KeyBinding(translationKey, InputUtil.Type.KEYSYM, keyBind, category);
        
        return KeyBindingHelper.registerKeyBinding(keyBinding);
    }
}
