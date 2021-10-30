package me.bionicbeanie.mods.savecoords.gui.impl;

import me.bionicbeanie.mods.savecoords.gui.IGuiController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

class GuiController implements IGuiController {

    private MinecraftClient client;

    public GuiController(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void openScreen(Screen screen) {
        client.setScreen(screen);
    }

    @Override
    public void closeScreen() {
        client.setScreen(null);
    }
}
