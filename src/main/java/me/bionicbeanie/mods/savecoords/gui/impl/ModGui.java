package me.bionicbeanie.mods.savecoords.gui.impl;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.gui.IGuiController;
import me.bionicbeanie.mods.savecoords.gui.IKeyBindConfiguration;
import me.bionicbeanie.mods.savecoords.impl.Factory;
import net.minecraft.client.MinecraftClient;

public class ModGui {

    public static void start(MinecraftClient client, IFileStore fileStore, IKeyBindConfiguration keyBindConfiguration) {
        IGuiController controller = new GuiController(client);
        IPlayerLocator locator = Factory.CreatePlayerLocator(client);
        
        new SaveCoordinatesGui(fileStore, locator, keyBindConfiguration, controller);
    }
}
