package me.bionicbeanie.mods.savecoords.gui.impl;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IKeyBinds;
import me.bionicbeanie.mods.savecoords.IModGui;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.impl.Factory;
import net.minecraft.client.MinecraftClient;

//All of this works because single-threaded initialization!! Sed lyf :(
public class DIContainer {

    private static boolean initialized = false;
    private static IFileStore fileStore;
    private static GuiController guiController;
    private static IPlayerLocator playerLocator;
    private static IModGui modGui;
    private static IKeyBinds keyBinds;
    //private static ConfigScreenFactory<Screen> modMenuScreenFactory;
    private static PingPositionOperation pingPositionOperation;

    public static IModGui getModGui() {
        initialize();
        return modGui;
    }

    public static IKeyBinds getKeyBinds() {
        initialize();
        return keyBinds;
    }

//    public static ConfigScreenFactory<Screen> getModMenuScreenFactory() {
//        initialize();
//
//        if (modMenuScreenFactory == null) {
//            modMenuScreenFactory = (parent) -> {
//                ConfigViewHandler handler = new ConfigViewHandler();
//
//                handler.onSave(() -> {
//                    new SaveConfigsOperation(keyBinds, fileStore, handler::getState).run();
//                    guiController.closeScreen();
//                });
//
//                handler.onBack(() -> guiController.closeScreen());
//
//                return handler.createView(keyBinds.getAllBinds());
//            };
//        }
//        return modMenuScreenFactory;
//    }
    
    public static Runnable getPingPositionOperation() {
        initialize();
        return pingPositionOperation;
    }

    private static void initialize() {
        if (!initialized) {
            initialized = true;

            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            fileStore = Factory.createFileStore(minecraftClient.runDirectory.getAbsolutePath());
            guiController = new GuiController(minecraftClient);
            playerLocator = Factory.CreatePlayerLocator(minecraftClient);
            keyBinds = Factory.CreateKeyBinds(fileStore);
            modGui = new SaveCoordinatesGui(fileStore, playerLocator, keyBinds, guiController);
            
            pingPositionOperation = new PingPositionOperation(fileStore, () -> playerLocator.locate());
        }
    }
}
