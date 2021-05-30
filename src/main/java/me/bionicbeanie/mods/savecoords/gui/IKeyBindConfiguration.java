package me.bionicbeanie.mods.savecoords.gui;

import net.minecraft.client.options.KeyBinding;

public interface IKeyBindConfiguration {

    KeyBinding getDefaultKeyBinding();
    
    void setDefaultKeyBinding(int keyCode);
}
