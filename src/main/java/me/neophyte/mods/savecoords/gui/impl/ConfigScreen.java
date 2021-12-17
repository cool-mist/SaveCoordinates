package me.neophyte.mods.savecoords.gui.impl;

import java.util.function.BiConsumer;

import io.github.cottonmc.cotton.gui.GuiDescription;
import me.neophyte.mods.savecoords.gui.SaveCoordinatesScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Type;

public class ConfigScreen extends SaveCoordinatesScreen {

    private BiConsumer<Type, Integer> keyCodeConsumer;

    public ConfigScreen(GuiDescription description, BiConsumer<Type, Integer> keyCodeConsumer) {
        super(description);

        this.keyCodeConsumer = keyCodeConsumer;
    }

    @Override
    public boolean keyReleased(int ch, int keyCode, int modifiers) {
        keyCodeConsumer.accept(InputUtil.Type.KEYSYM, ch);
        return super.keyReleased(ch, keyCode, modifiers);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        keyCodeConsumer.accept(InputUtil.Type.MOUSE, mouseButton);
        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }
}
