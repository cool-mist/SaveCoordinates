package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.function.Consumer;

import io.github.cottonmc.cotton.gui.GuiDescription;
import me.bionicbeanie.mods.savecoords.gui.SaveCoordinatesScreen;

public class ConfigScreen extends SaveCoordinatesScreen{

    private Consumer<Integer> keyCodeConsumer;

    public ConfigScreen(GuiDescription description, Consumer<Integer> keyCodeConsumer) {
        super(description);
        
        this.keyCodeConsumer = keyCodeConsumer;
    }
    
    @Override
    public boolean keyReleased(int ch, int keyCode, int modifiers) {
        keyCodeConsumer.accept(ch);
        return super.keyReleased(ch, keyCode, modifiers);
    }
}
