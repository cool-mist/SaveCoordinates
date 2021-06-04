package me.bionicbeanie.mods.savecoords.model;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.util.InputUtil.Type;

public class ConfigData {

    private Config[] keyConfigs;
    
    @Deprecated
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
    
    public Config[] getKeyConfigs() {
        return keyConfigs;
    }

    public void setKeyConfigs(Config[] keyConfigs) {
        this.keyConfigs = keyConfigs;
    }

    public static class Config{
        private String name;
        private Type type;
        private int code;
        
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public Type getType() {
            return type;
        }
        public void setType(Type type) {
            this.type = type;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
