package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.gui.IKeyBindConfiguration;
import me.bionicbeanie.mods.savecoords.model.ConfigData;

public class SaveConfigsOperation extends ViewOperationBase<ConfigData>{

    private IKeyBindConfiguration keyBindConfiguration;

    public SaveConfigsOperation(IFileStore fileStore, IKeyBindConfiguration keyBindConfiguration, Supplier<ConfigData> stateSupplier) {
        super(fileStore, stateSupplier);
        
        this.keyBindConfiguration = keyBindConfiguration;
    }

    @Override
    protected void executeOperation(IFileStore fileStore, ConfigData state) throws Exception {
        fileStore.writeConfigs(state);
        this.keyBindConfiguration.setDefaultKeyBinding(state.getDefaultKeyBindingCode());
    }
}
