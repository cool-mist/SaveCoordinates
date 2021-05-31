package me.bionicbeanie.mods.savecoords;

import me.bionicbeanie.mods.savecoords.gui.impl.ModDI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class SaveCoordinatesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ModDI.getKeyBindConfiguration().getDefaultKeyBinding().wasPressed()) {
                ModDI.getModGui().open();
            }
        });
    }
}
