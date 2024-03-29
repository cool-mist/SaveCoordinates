package me.neophyte.mods.savecoords.gui.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import me.neophyte.mods.savecoords.IFileStore;
import me.neophyte.mods.savecoords.IKeyBinds;
import me.neophyte.mods.savecoords.IKeyBinds.IKeyBinding;
import me.neophyte.mods.savecoords.model.ConfigData;
import me.neophyte.mods.savecoords.model.ConfigData.Config;
import net.minecraft.client.util.InputUtil.Type;

public class SaveConfigsOperation extends ViewOperationBase<List<IKeyBinding>>{

    private IKeyBinds keyBinds;

    public SaveConfigsOperation(IKeyBinds keyBinds, IFileStore fileStore, Supplier<List<IKeyBinding>> stateSupplier) {
        super(fileStore, stateSupplier);
        
        this.keyBinds = keyBinds;
    }

    @Override
    protected void executeOperation(IFileStore fileStore, List<IKeyBinding> state) throws Exception {
        
        ConfigData configData = new ConfigData();
        List<Config> configsToWrite = new ArrayList<>();
        
        for (IKeyBinding binding : state) {
            Config config = new Config();
            config.setName(binding.getName());
            config.setType(parseTypeString(binding.getType()));
            config.setCode(binding.getCode());
            
            keyBinds.updateKeyBind(binding.getName(), binding.getType(), binding.getCode());
            
            configsToWrite.add(config);
        }
        
        configData.setKeyConfigs(configsToWrite.toArray(new Config[] {}));
        
        fileStore.writeConfigs(configData);
    }

    private String parseTypeString(Type type) {
        if(type == Type.KEYSYM) {
            return "KEYSYM";
        }
        
        return "MOUSE";
    }
}
