package me.neophyte.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import me.neophyte.mods.savecoords.IFileStore;
import me.neophyte.mods.savecoords.model.PlayerRawPosition;
import net.minecraft.client.MinecraftClient;

class PingPositionOperation extends ViewOperationBase<PlayerRawPosition> {

    public PingPositionOperation(IFileStore fileStore, Supplier<PlayerRawPosition> stateSupplier) {
        super(fileStore, stateSupplier);
    }

    @SuppressWarnings("resource")
    @Override
    protected void executeOperation(IFileStore fileStore, PlayerRawPosition position) throws Exception {
        MinecraftClient.getInstance().player.sendChatMessage(position.toString());
    }
}
