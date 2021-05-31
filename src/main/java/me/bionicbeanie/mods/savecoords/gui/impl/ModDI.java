package me.bionicbeanie.mods.savecoords.gui.impl;

import java.io.IOException;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IModGui;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.gui.IKeyBindConfiguration;
import me.bionicbeanie.mods.savecoords.impl.Factory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

//All of this works because single-threaded initialization!! Sed lyf :(
public class ModDI {
    
    private static boolean initialized = false;
    private static IFileStore fileStore;
    private static IKeyBindConfiguration keyBindConfiguration;
    private static GuiController guiController;
    private static IPlayerLocator playerLocator;
    private static IModGui modGui;
    private static ConfigScreenFactory<Screen> modMenuScreenFactory;
    
    public static IKeyBindConfiguration getKeyBindConfiguration() {
        initialize();
        return keyBindConfiguration;
    }
    
    public static IModGui getModGui() {
        initialize();
        return modGui;
    }
    
    public static ConfigScreenFactory<Screen> getModMenuScreenFactory(){
        if(modMenuScreenFactory == null) {
            modMenuScreenFactory = (parent) -> {
                ConfigViewHandler handler = new ConfigViewHandler();

                handler.onSave(() -> {
                    new SaveConfigsOperation(keyBindConfiguration, handler::getState).run();
                    guiController.openScreen(parent);
                });

                handler.onBack(() -> guiController.openScreen(parent));
                
                try {
                    return handler.createView(fileStore.readConfigData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                return null;
            };
        }
        return modMenuScreenFactory;
    }
    
    private static void initialize() {
        if(!initialized) {
            
            initialized = true;
            
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            fileStore = Factory.createFileStore(minecraftClient.runDirectory.getAbsolutePath());
            keyBindConfiguration = Factory.createKeyBindConfiguration(fileStore);
            guiController = new GuiController(minecraftClient);
            playerLocator = Factory.CreatePlayerLocator(minecraftClient);
            modGui = new SaveCoordinatesGui(fileStore, playerLocator, keyBindConfiguration, guiController);
        }
    }
}
