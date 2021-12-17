package me.neophyte.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import me.neophyte.mods.savecoords.IFileStore;

class DeletePositionOperation extends ViewOperationBase<String> {

    public DeletePositionOperation(IFileStore fileStore, Supplier<String> stateSupplier) {
        super(fileStore, stateSupplier);
    }

    @Override
    protected void executeOperation(IFileStore fileStore, String positionId) throws Exception {
        fileStore.deletePosition(positionId);
    }
}
