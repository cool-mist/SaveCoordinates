package me.bionicbeanie.mods.gui.view;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.api.IViewHandler;
import net.minecraft.client.MinecraftClient;

public abstract class ViewHandlerBase implements IViewHandler {

    protected IFileStore fileStore;
    protected IGui gui;
    protected MinecraftClient client;
    protected WWidget panel;

    protected ViewHandlerBase(IFileStore fileStore, IGui gui, MinecraftClient client) {
        this.fileStore = fileStore;
        this.gui = gui;
        this.client = client;
    }

}
