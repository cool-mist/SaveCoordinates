package me.neophyte.mods.savecoords;

import me.neophyte.mods.savecoords.IKeyBinds.IKeyBinding;
import me.neophyte.mods.savecoords.gui.impl.DIContainer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class SaveCoordinatesClient implements ClientModInitializer {

    private static IKeyBinding defaultKeyBinding = getKeyBinding(IKeyBinds.DEFAULT);
    private static IKeyBinding pingKeyBinding = getKeyBinding(IKeyBinds.PING);
    private static IKeyBinding pingLockBinding = getKeyBinding(IKeyBinds.PING_LOCK);
    private static IKeyBinding listKeyBinding = getKeyBinding(IKeyBinds.LIST);

    @SuppressWarnings("resource")
    @Override
    public void onInitializeClient() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(defaultKeyBinding.wasPressed()) {
                DIContainer.getModGui().open();
            }
            
            while(listKeyBinding.wasPressed()) {
                DIContainer.getModGui().openList();
            }
            
            while(pingLockBinding.wasPressed()) {
                boolean enabled = DIContainer.togglePingBehavior();
                // TODO : Abstract to a tooltip queue
                String translationKey = TranslationKeys.TOOLTIP_PING_DISABLED;
                if(enabled) {
                    translationKey = TranslationKeys.TOOLTIP_PING_ENABLED;
                }
                
                MinecraftClient.getInstance().player.sendMessage(new TranslatableText(translationKey), true);
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
