package me.bionicbeanie.mods.gui;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.api.IGuiHandler;
import net.minecraft.client.MinecraftClient;

public abstract class GuiHandlerBase implements IGuiHandler {

    protected IFileStore fileStore;
    protected IGui gui;
    protected MinecraftClient client;
    protected WWidget panel;

    protected GuiHandlerBase(IFileStore fileStore, IGui gui, MinecraftClient client) {
        this.fileStore = fileStore;
        this.gui = gui;
        this.client = client;
    }

}
