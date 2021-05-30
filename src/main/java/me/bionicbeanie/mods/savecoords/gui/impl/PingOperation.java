package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import net.minecraft.client.MinecraftClient;

class PingOperation extends ViewOperationBase<PlayerRawPosition>{

    public PingOperation(IFileStore fileStore, Supplier<PlayerRawPosition> stateSupplier) {
        super(fileStore, stateSupplier);
    }

    @SuppressWarnings("resource")
    @Override
    protected void executeOperation(IFileStore fileStore, PlayerRawPosition position) throws Exception {
        MinecraftClient.getInstance().player.sendChatMessage(position.toString());
    }
}
