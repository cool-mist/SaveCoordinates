package me.bionicbeanie.mods.savecoords;

import me.bionicbeanie.mods.savecoords.gui.IKeyBindConfiguration;
import me.bionicbeanie.mods.savecoords.gui.impl.ModGui;
import me.bionicbeanie.mods.savecoords.impl.Factory;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class SaveCoordinatesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        @SuppressWarnings("resource")
        IFileStore fileStore = Factory.createFileStore(MinecraftClient.getInstance().runDirectory.getAbsolutePath());
        IKeyBindConfiguration keyBindConfiguration = Factory.createKeyBindConfiguration(fileStore);
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindConfiguration.getDefaultKeyBinding().wasPressed()) {
                ModGui.start(client, fileStore, keyBindConfiguration);
            }
        });
    }
}
