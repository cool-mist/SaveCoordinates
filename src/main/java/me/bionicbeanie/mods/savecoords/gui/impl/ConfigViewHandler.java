package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import me.bionicbeanie.mods.savecoords.gui.IRootPanel;
import me.bionicbeanie.mods.savecoords.model.ConfigData;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.text.LiteralText;

class ConfigViewHandler extends ViewHandlerBase<ConfigData> {

    private WButton keyBindingButton;
    private WButton saveButton;
    private WButton backButton;
    private boolean focussing = false;
    private int newKeyBinding = -1;
    private WButton resetButton;

    public ConfigViewHandler() {
        keyBindingButton = new WButton();
        saveButton = new WButton(new LiteralText("SAVE"));
        backButton = new WButton(new LiteralText("BACK"));
        resetButton = new WButton(new LiteralText("RESET"));
    }
    
    @Override
    protected Supplier<ConfigData> setupView(IRootPanel rootGridPanel, ConfigData state) {

        if (state == null) {
            state = new ConfigData();
        }

        newKeyBinding = state.getDefaultKeyBindingCode();

        focussing = false;

        WLabel configLabel = new WLabel("Default Key Binding");
        rootGridPanel.add(configLabel, 4, 3, 3, 1);

        setConfigValue(state.getDefaultKeyBindingCode());

        keyBindingButton.setOnClick(() -> {
            focussing = !focussing;
            if (focussing) {
                keyBindingButton.setLabel(new LiteralText("_"));
            }
        });
        
        resetButton.setOnClick(() -> {
            focussing = true;
            setConfigValue(new ConfigData().getDefaultKeyBindingCode());
            focussing = false;
        });

        rootGridPanel.add(keyBindingButton, 4, 4, 3, 1);
        rootGridPanel.add(saveButton, 13, 9, 2, 1);
        rootGridPanel.add(backButton, 0, 9, 2, 1);
        rootGridPanel.add(resetButton, 8, 4, 2, 1);

        return () -> {
            ConfigData data = new ConfigData();
            data.setDefaultKeyBindingCode(newKeyBinding);

            return data;
        };
    }

    @Override
    protected Screen createScreen() {
        return new ConfigScreen(this, (k) -> {
            if (focussing) {
                setConfigValue(k);
                focussing = false;
            }
        });
    }
    
    public void onBack(Runnable runnable) {
        this.backButton.setOnClick(runnable);
    }
    
    public void onSave(Runnable runnable) {
        this.saveButton.setOnClick(runnable);
    }

    private void setConfigValue(Integer k) {
        newKeyBinding = k;
        keyBindingButton.setLabel(new LiteralText(parseKeyName(k)));
    }

    private String parseKeyName(Integer k) {
        Key key = InputUtil.Type.KEYSYM.createFromCode(k);
        String keyString = key.toString();

        try {
            return keyString.split("\\.")[2];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return keyString;
    }
}
