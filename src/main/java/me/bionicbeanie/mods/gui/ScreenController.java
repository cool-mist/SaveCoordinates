package me.bionicbeanie.mods.gui;

import me.bionicbeanie.mods.api.IScreenController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class ScreenController implements IScreenController {

    private MinecraftClient client;

    public ScreenController(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void openScreen(Screen screen) {
        client.openScreen(screen);
    }

    @Override
    public void closeScreen() {
        client.openScreen(null);
    }
}
