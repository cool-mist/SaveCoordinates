package me.bionicbeanie.mods.savecoords.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IKeyBinds;
import me.bionicbeanie.mods.savecoords.TranslationKeys;
import me.bionicbeanie.mods.savecoords.model.ConfigData;
import me.bionicbeanie.mods.savecoords.model.ConfigData.Config;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

class KeyBinds implements IKeyBinds {

    KeyBindingEx DEFAULT = new KeyBindingEx(IKeyBinds.DEFAULT, TranslationKeys.KEYBIND_DEFAULT, InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H, TranslationKeys.CATEGORY_GENERIC);

    KeyBindingEx PING = new KeyBindingEx(IKeyBinds.PING, TranslationKeys.KEYBIND_PING, InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B, TranslationKeys.CATEGORY_GENERIC);

    KeyBindingEx PING_LOCK = new KeyBindingEx(IKeyBinds.PING_LOCK, TranslationKeys.KEYBIND_PING_LOCK,
            InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_N, TranslationKeys.CATEGORY_GENERIC);

    private IFileStore fileStore;
    private Map<String, KeyBindingEx> keyBinds;
    private List<IKeyBinding> unmodifiableList;

    KeyBinds(IFileStore fileStore) {
        this.fileStore = fileStore;
        this.keyBinds = new HashMap<>();

        add(DEFAULT);
        add(PING);
        add(PING_LOCK);

        initialize();

        this.unmodifiableList = Collections.unmodifiableList(new ArrayList<>(keyBinds.values()));
    }

    private void add(KeyBindingEx keyBinding) {
        keyBinds.put(keyBinding.getName(), keyBinding);
    }

    @Override
    public List<IKeyBinding> getAllBinds() {
        return unmodifiableList;
    }

    @Override
    public IKeyBinding getKeyBind(String name) {
        return keyBinds.get(name);
    }

    @Override
    public void updateKeyBind(String name, Type type, int code) {
        keyBinds.get(name).setBoundKey(type, code);
        KeyBinding.updateKeysByCode();
    }

    static class KeyBindingEx extends KeyBinding implements IKeyBinding {

        private Type defaultType;
        private Type type;
        private int defaultCode;
        private int code;
        private String name;

        public KeyBindingEx(String name, String translationKey, Type type, int code, String category) {
            super(translationKey, type, code, category);

            this.type = this.defaultType = type;
            this.code = this.defaultCode = code;
            this.name = name;
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public Type getDefaultType() {
            return defaultType;
        }

        @Override
        public int getDefaultCode() {
            return defaultCode;
        }

        @Override
        public boolean wasPressed() {
            return super.wasPressed();
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Text getNameLocalizedText() {
            return new TranslatableText(getTranslationKey());
        }

        @Override
        public Text getBoundKeyLocalizedText() {
            return super.getBoundKeyLocalizedText();
        }

        void setBoundKey(Type type, int code) {
            super.setBoundKey(type.createFromCode(code));

            this.type = type;
            this.code = code;
        }
    }

    private void initialize() {
        try {
            ConfigData configData = fileStore.readConfigData();

            Config[] configs = configData.getKeyConfigs();

            if (configs == null) {
                return;
            }

            for (int i = 0; i < configs.length; ++i) {
                String name = configs[i].getName();
                Type type = parseType(configs[i].getType());
                int code = configs[i].getCode();

                if (keyBinds.containsKey(name)) {
                    updateKeyBind(name, type, code);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Type parseType(String type) {
        if ("KEYSYM".equalsIgnoreCase(type)) {
            return Type.KEYSYM;
        }

        return Type.MOUSE;
    }
}
