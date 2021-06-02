package me.bionicbeanie.mods.savecoords.gui.impl;

import java.io.IOException;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IModGui;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.impl.Factory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

//All of this works because single-threaded initialization!! Sed lyf :(
public class ModDI {
    
    private static boolean initialized = false;
    private static IFileStore fileStore;
    private static GuiController guiController;
    private static IPlayerLocator playerLocator;
    private static IModGui modGui;
    private static ConfigScreenFactory<Screen> modMenuScreenFactory;
    
    public static IModGui getModGui() {
        initialize();
        return modGui;
    }
    
    private static void initialize() {
        if(!initialized) {
            
            initialized = true;
            
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            fileStore = Factory.createFileStore(minecraftClient.runDirectory.getAbsolutePath());
            guiController = new GuiController(minecraftClient);
            playerLocator = Factory.CreatePlayerLocator(minecraftClient);
            modGui = new SaveCoordinatesGui(fileStore, playerLocator, guiController);
        }
    }
}
