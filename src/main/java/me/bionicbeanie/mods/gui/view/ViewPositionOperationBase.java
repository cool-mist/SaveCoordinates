package me.bionicbeanie.mods.gui.view;

import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.model.PlayerPosition;

public abstract class ViewPositionOperationBase extends ViewOperationBase{

    private PlayerPosition position;

    public ViewPositionOperationBase(IFileStore fileStore, IGui gui, PlayerPosition position) {
        super(fileStore, gui);
        this.position = position;
    }
    
    @Override
    protected void executeOperation(IFileStore fileStore, IGui gui) throws Exception {
        executeOperation(fileStore, gui, position);
    }

    protected abstract void executeOperation(IFileStore fileStore, IGui gui, PlayerPosition position) throws Exception;
}
