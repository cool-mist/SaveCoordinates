package me.bionicbeanie.mods.savecoords.impl;

import java.io.IOException;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.gui.IKeyBindConfiguration;
import me.bionicbeanie.mods.savecoords.model.ConfigData;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

class KeyBindConfiguration implements IKeyBindConfiguration {

    KeyBinding defaultKeyBinding;

    KeyBindConfiguration(IFileStore fileStore) {
        try {
            defaultKeyBinding = createDefaultKeyBinding(fileStore);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public KeyBinding getDefaultKeyBinding() {
        return defaultKeyBinding;
    }

    @Override
    public void setDefaultKeyBinding(int keyCode) {
        defaultKeyBinding.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(keyCode));
        KeyBinding.updateKeysByCode();
    }

    private KeyBinding createDefaultKeyBinding(IFileStore fileStore) throws IOException {

        ConfigData configData = fileStore.readConfigData();

        if (configData == null) {
            configData = new ConfigData();
        }

        int keyBind = configData.getDefaultKeyBindingCode();

        return createKeyBinding(keyBind);
    }

    private KeyBinding createKeyBinding(int keyBind) {
        String translationKey = "key.savecoords.coords";
        String category = "category.savecoords.generic";

        KeyBinding keyBinding = new KeyBinding(translationKey, InputUtil.Type.KEYSYM, keyBind, category);

        return keyBinding;
    }

}
