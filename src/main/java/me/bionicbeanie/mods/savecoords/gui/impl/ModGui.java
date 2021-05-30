package me.bionicbeanie.mods.savecoords.gui.impl;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.gui.IGuiController;
import me.bionicbeanie.mods.savecoords.impl.Factory;
import net.minecraft.client.MinecraftClient;

public class ModGui {

    public static void start(MinecraftClient client) {
        IGuiController controller = new GuiController(client);
        IPlayerLocator locator = Factory.CreatePlayerLocator(client);
        IFileStore fileStore = Factory.createFileStore(client.runDirectory.getAbsolutePath());
        
        new SaveCoordinatesGui(fileStore, locator, controller);
    }
}
