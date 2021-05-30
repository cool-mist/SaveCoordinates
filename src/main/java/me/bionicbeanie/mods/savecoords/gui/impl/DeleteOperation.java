package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import me.bionicbeanie.mods.savecoords.IFileStore;

class DeleteOperation extends ViewOperationBase<String> {

    public DeleteOperation(IFileStore fileStore, Supplier<String> stateSupplier) {
        super(fileStore, stateSupplier);
    }

    @Override
    protected void executeOperation(IFileStore fileStore, String positionId) throws Exception {
        fileStore.delete(positionId);
    }
}
