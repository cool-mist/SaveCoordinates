package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.model.PlayerPosition;

class SavePositionOperation extends ViewOperationBase<PlayerPosition> {

    public SavePositionOperation(IFileStore fileStore, Supplier<PlayerPosition> stateSupplier) {
        super(fileStore, stateSupplier);
    }

    @Override
    protected void executeOperation(IFileStore fileStore, PlayerPosition position) throws Exception {
        fileStore.writePosition(position);
        fileStore.writeDefaultWorldName(position.getPositionMetadata().getWorldName());
    }
}
