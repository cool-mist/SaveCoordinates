package me.bionicbeanie.mods.gui.view;

import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.model.PlayerPosition;

public class DeleteOperation extends ViewPositionOperationBase {

    public DeleteOperation(IFileStore fileStore, IGui gui, PlayerPosition position) {
        super(fileStore, gui, position);
    }

    @Override
    protected void executeOperation(IFileStore fileStore, IGui gui, PlayerPosition position) throws Exception {
        fileStore.delete(position.getId());
        gui.showListView();
    }
}
