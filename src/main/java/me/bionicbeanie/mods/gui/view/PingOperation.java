package me.bionicbeanie.mods.gui.view;

import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import net.minecraft.client.MinecraftClient;

public class PingOperation extends ViewOperationBase{

    private PlayerRawPosition position;

    public PingOperation(IFileStore fileStore, IGui gui, PlayerRawPosition position) {
        super(fileStore, gui);
        
        this.position = position;
    }

    @SuppressWarnings("resource")
    @Override
    protected void executeOperation(IFileStore fileStore, IGui gui) throws Exception {
        MinecraftClient.getInstance().player.sendChatMessage(position.toString());
    }
}
