package me.bionicbeanie.mods.savecoords.model;

import org.lwjgl.glfw.GLFW;

public class ConfigData {

    private Config[] keyConfigs;
    
    @Deprecated
    private int defaultKeyBindingCode;
    
    public ConfigData() {
        this.defaultKeyBindingCode = GLFW.GLFW_KEY_H;
    }
    
    @Deprecated
    public void setDefaultKeyBindingCode(int defaultKeyBindingCode) {
        this.defaultKeyBindingCode = defaultKeyBindingCode;
    }
    
    @Deprecated
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
        private String type;
        private int code;
        
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
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
