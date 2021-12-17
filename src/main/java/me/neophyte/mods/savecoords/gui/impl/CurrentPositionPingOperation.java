package me.neophyte.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import me.neophyte.mods.savecoords.IFileStore;
import me.neophyte.mods.savecoords.TranslationKeys;
import me.neophyte.mods.savecoords.model.PlayerRawPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class CurrentPositionPingOperation extends PingPositionOperation{

    private boolean enabled = false;
    
    public CurrentPositionPingOperation(IFileStore fileStore, Supplier<PlayerRawPosition> stateSupplier) {
        super(fileStore, stateSupplier);
    }
    
    public boolean toggleEnabled() {
        this.enabled = !this.enabled;
        
        return this.enabled;
    }
    
    @SuppressWarnings("resource")
    @Override
    protected void executeOperation(IFileStore fileStore, PlayerRawPosition position) throws Exception {
        if (enabled) {
            super.executeOperation(fileStore, position);
        } else {
            MinecraftClient.getInstance().player.sendMessage(new TranslatableText(TranslationKeys.TOOLTIP_PING_LOCK),
                    true);
        }
    }

}
