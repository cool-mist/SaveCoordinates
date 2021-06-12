package me.bionicbeanie.mods.savecoords.model;

import java.lang.reflect.Type;

import org.lwjgl.glfw.GLFW;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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

    public static class Config {
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

    public static class ConfigDeserializer implements JsonDeserializer<Config> {

        @Override
        public Config deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String inputType = "KEYSYM";
            if (jsonObject.has("type")) {
                JsonElement elem = jsonObject.get("type");
                if("1".equalsIgnoreCase(elem.getAsString())) {
                    inputType = "MOUSE";
                }else if("MOUSE".equalsIgnoreCase(elem.getAsString())) {
                        inputType = "MOUSE";
                }
            }
            Config config = new Config();
            
            config.setCode(jsonObject.get("code").getAsInt());
            config.setName(jsonObject.get("name").getAsString());
            config.setType(inputType);
            
            return config;
        }
    }
}
