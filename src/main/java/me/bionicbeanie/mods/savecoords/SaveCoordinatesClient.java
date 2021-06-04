package me.bionicbeanie.mods.savecoords;

import me.bionicbeanie.mods.savecoords.IKeyBinds.IKeyBinding;
import me.bionicbeanie.mods.savecoords.gui.impl.DIContainer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class SaveCoordinatesClient implements ClientModInitializer {

    private static IKeyBinding defaultKeyBinding = getKeyBinding(IKeyBinds.DEFAULT);
    private static IKeyBinding pingKeyBinding = getKeyBinding(IKeyBinds.PING);
    
    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(defaultKeyBinding.wasPressed()) {
                DIContainer.getModGui().open();
            }
            
            while(pingKeyBinding.wasPressed()) {
                DIContainer.getPingPositionOperation().run();
            }
        });
    }

    private static IKeyBinding getKeyBinding(String name) {
        return DIContainer.getKeyBinds().getKeyBind(name);
    }
}
