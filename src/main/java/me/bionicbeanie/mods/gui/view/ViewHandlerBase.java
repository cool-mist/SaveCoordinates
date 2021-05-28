package me.bionicbeanie.mods.gui.view;

import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.api.IRootGridPanel;
import me.bionicbeanie.mods.api.IViewHandler;
import net.minecraft.client.MinecraftClient;

public abstract class ViewHandlerBase<T> implements IViewHandler<T> {

    protected IFileStore fileStore;
    protected IGui gui;
    protected MinecraftClient client;
    protected WWidget panel;
    private T state;

    protected ViewHandlerBase(IFileStore fileStore, IGui gui, MinecraftClient client) {
        this.fileStore = fileStore;
        this.gui = gui;
        this.client = client;
    }

    protected abstract void placeWidgets(IRootGridPanel rootPanel, T state);

    @Override
    public void clearState() {
        state = null;
    }

    @Override
    public void setState(T state) {
        this.state = state;
    }

    @Override
    public void placeWidgets(IRootGridPanel rootPanel) {
        placeWidgets(rootPanel, state);
    }

}
