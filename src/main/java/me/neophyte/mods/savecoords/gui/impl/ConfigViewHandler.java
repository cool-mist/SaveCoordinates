package me.neophyte.mods.savecoords.gui.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import me.neophyte.mods.savecoords.TranslationKeys;
import me.neophyte.mods.savecoords.IKeyBinds.IKeyBinding;
import me.neophyte.mods.savecoords.gui.IRootPanel;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

// TODO:
//      Clean the mess below on order of event handling
//      Remove `shouldNotUpdateBinding

class ConfigViewHandler extends ViewHandlerBase<List<IKeyBinding>> {

    private ConfigItemPanel focussingConfig;
    private WButton saveButton;
    private WButton backButton;
    private WButton resetButton;
    private boolean shouldNotUpdateBinding = false;
    private ConfigListPanel listPanel;
    
    public ConfigViewHandler() {
        this.saveButton = new WButton(new TranslatableText(TranslationKeys.MENU_SAVE));
        this.backButton = new WButton(new TranslatableText(TranslationKeys.MENU_BACK));
        this.resetButton = new WButton(new TranslatableText(TranslationKeys.MENU_RESET));
    }

    @Override
    protected Supplier<List<IKeyBinding>> setupView(IRootPanel rootGridPanel, List<IKeyBinding> binds) {

        listPanel = createListPanel(binds);
        rootGridPanel.add(listPanel, 0, 0, 15, 9);
        
        rootGridPanel.add(resetButton, 10, 9, 2, 1);
        rootGridPanel.add(saveButton, 13, 9, 2, 1);
        rootGridPanel.add(backButton, 0, 9, 2, 1);
        
        this.resetButton.setOnClick(() -> {
            this.shouldNotUpdateBinding = true;
            this.reset();
        });

        return () -> {
            return listPanel.getState();
        };
    }

    private ConfigListPanel createListPanel(List<IKeyBinding> keyConfigs) {
        BiConsumer<IKeyBinding, ConfigItemPanel> configurator = (conf, p) -> p.setInitialConfig(conf);
        ConfigListPanel panel = createListPanel(keyConfigs, configurator);

        panel.setListItemHeight(2 * 18);

        return panel;
    }

    private ConfigListPanel createListPanel(List<IKeyBinding> keyConfigs,
            BiConsumer<IKeyBinding, ConfigItemPanel> configurator) {
        return new ConfigListPanel(keyConfigs, this::createConfigItemPanel, configurator);
    }
    
    private ConfigItemPanel createConfigItemPanel() {
        ConfigItemPanel panel = new ConfigItemPanel();
        panel.setOnFocus((k) -> {
            if(focussingConfig != null) {
                focussingConfig.reset();
            }
            this.focussingConfig = k;
        });
        
        return panel;
    }

    @Override
    protected Screen createScreen() {
        return new ConfigScreen(this, (t, c) -> {
            
            if(shouldNotUpdateBinding) {
                shouldNotUpdateBinding = false;
                return;
            }
            
            if(this.focussingConfig == null) {
                return;
            }
            
            this.registerKeybind(t, c);
        });
    }

    void onBackButtonClick(Runnable runnable) {
        this.backButton.setOnClick(() -> {
            runnable.run();
        });
    }

    void onSaveButtonClick(Runnable runnable) {
        this.saveButton.setOnClick(() -> {
            this.shouldNotUpdateBinding = true;
            if(this.focussingConfig != null) {
                this.focussingConfig.reset();
            }
            runnable.run();
        });
    }
    
    private void registerKeybind(Type type, int code) {
        
        if(this.focussingConfig != null) {
            focussingConfig.updateBinding(type, code);
            focussingConfig = null;
        }
        
        return;
    }
    
    private void reset() {
        listPanel.reset();
        focussingConfig = null;
    }

    private class ConfigListPanel extends WListPanel<IKeyBinding, ConfigItemPanel>{

        public ConfigListPanel(List<IKeyBinding> data, Supplier<ConfigItemPanel> supplier,
                BiConsumer<IKeyBinding, ConfigItemPanel> configurator) {
            super(data, supplier, configurator);
        }
        
        List<IKeyBinding> getState(){
            List<IKeyBinding> keyBinds = new ArrayList<>();
            this.configured.forEach((k, p) -> {
                keyBinds.add(new KeyData(p.getType(), p.getCode(), k));
            });
            
            return keyBinds;
        }
        
        void reset() {
            this.configured.forEach((k, p) -> p.reset());
        }
    }
    
    private class KeyData implements IKeyBinding{

        private Type type;
        private int code;
        private IKeyBinding existingBinding;

        KeyData(Type type, int code, IKeyBinding binding){
            this.type = type;
            this.code= code;
            this.existingBinding= binding;
        }
        
        @Override
        public String getName() {
            return existingBinding.getName();
        }

        @Override
        public boolean wasPressed() {
            return existingBinding.wasPressed();
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public Text getBoundKeyLocalizedText() {
            return existingBinding.getBoundKeyLocalizedText();
        }

        @Override
        public Text getNameLocalizedText() {
            return existingBinding.getNameLocalizedText();
        }

        @Override
        public Type getDefaultType() {
            return existingBinding.getDefaultType();
        }

        @Override
        public int getDefaultCode() {
            return existingBinding.getDefaultCode();
        }
    }
    
    private class ConfigItemPanel extends WPlainPanel {
        private WButton configButton;
        private WButton resetButton;
        private WLabel configLabel;
        private boolean focussing = false;
        private Type defaultType;
        private int defaultCode;
        private Type type;
        private int code;

        ConfigItemPanel() {
            this.configLabel = new WLabel("");
            this.configButton = new WButton();
            this.resetButton = new WButton(new TranslatableText(TranslationKeys.MENU_RESET));
        }

        void setInitialConfig(IKeyBinding config) {
            this.defaultType = config.getDefaultType();
            this.defaultCode = config.getDefaultCode();
            this.type = config.getType();
            this.code = config.getCode();
            this.configLabel.setText(config.getNameLocalizedText());
            
            updateWidgetData(this.type, this.code);
            
            this.resetButton.setOnClick(this::reset);

            this.add(configLabel, 0, 0, 4 * 18, 1 * 9);
            this.add(configButton, 7 * 18, 0, 3 * 18, 1 * 18);
            this.add(resetButton, 11 * 18, 0, 3 * 18, 1 * 18);
        }
        
        void reset() {
            focussing = false;
            this.type = this.defaultType;
            this.code = this.defaultCode;
            updateWidgetData(this.defaultType, this.defaultCode);
        }
        
        void updateBinding(Type type, int code) {
            if(!focussing) {
                return;
            }
            this.type = type;
            this.code = code;
            
            focussing = false;
            updateWidgetData(type, code);
        }
        
        void setOnFocus(Consumer<ConfigItemPanel> focussedConfigConsumer) {
            this.configButton.setOnClick(() -> {
                if(!focussing) {
                    focussing = true;
                    focussedConfigConsumer.accept(this);
                }
                
                updateWidgetData(this.type, this.code);
            });
        }
        
        Type getType() {
            return type;
        }
        
        int getCode() {
            return code;
        }

        private void updateWidgetData(Type type, int code) {
            if(focussing) {
                this.configButton.setLabel(new LiteralText("_"));
                return;
            }
            
            this.configButton.setLabel(type.createFromCode(code).getLocalizedText());
        }
    }
}
