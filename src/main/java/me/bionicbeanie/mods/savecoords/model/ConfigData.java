package me.bionicbeanie.mods.savecoords.model;

import org.lwjgl.glfw.GLFW;

public class ConfigData {

    private int defaultKeyBindingCode;
    
    public ConfigData() {
        this.defaultKeyBindingCode = GLFW.GLFW_KEY_H; //Default value
    }
    
    public void setDefaultKeyBindingCode(int defaultKeyBindingCode) {
        this.defaultKeyBindingCode = defaultKeyBindingCode;
    }
    
    public int getDefaultKeyBindingCode() {
        return defaultKeyBindingCode;
    }
}
