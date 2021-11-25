package me.bionicbeanie.mods.savecoords.gui.impl;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;

import me.bionicbeanie.mods.savecoords.IDimensionAware;
import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IKeyBinds;
import me.bionicbeanie.mods.savecoords.IModGui;
import me.bionicbeanie.mods.savecoords.INetherCalculator;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.impl.Factory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

//All of this works because single-threaded initialization!! Sed lyf :(
public class DIContainer {

    private static boolean initialized = false;
    private static IFileStore fileStore;
    private static GuiController guiController;
    private static IPlayerLocator playerLocator;
    private static IModGui modGui;
    private static IKeyBinds keyBinds;
    private static ConfigScreenFactory<Screen> modMenuScreenFactory;
    private static CurrentPositionPingOperation pingPositionOperation;
    private static IDimensionAware dimensionAware;
    private static INetherCalculator netherCalculator;

    public static IModGui getModGui() {
        initialize();
        return modGui;
    }

    public static IKeyBinds getKeyBinds() {
        initialize();
        return keyBinds;
    }

    public static ConfigScreenFactory<Screen> getModMenuScreenFactory() {
        initialize();

        if (modMenuScreenFactory == null) {
            modMenuScreenFactory = (parent) -> {
                ConfigViewHandler handler = new ConfigViewHandler();

                handler.onSaveButtonClick(() -> {
                    new SaveConfigsOperation(keyBinds, fileStore, handler::getState).call();
                    guiController.closeScreen();
                });

                handler.onBackButtonClick(() -> guiController.closeScreen());

                return handler.createView(keyBinds.getAllBinds());
            };
        }
        return modMenuScreenFactory;
    }

    public static Runnable getPingPositionOperation() {
        initialize();
        return () -> {
            try {
                pingPositionOperation.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    public static boolean togglePingBehavior() {
        return pingPositionOperation.toggleEnabled();
    }

    private static void initialize() {
        if (!initialized) {
            initialized = true;

            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            fileStore = Factory.createFileStore(minecraftClient.runDirectory.getAbsolutePath());
            guiController = new GuiController(minecraftClient);
            playerLocator = Factory.CreatePlayerLocator(minecraftClient);
            keyBinds = Factory.CreateKeyBinds(fileStore);
            dimensionAware = Factory.CreateDimensionAware(minecraftClient);
            netherCalculator = Factory.CreateNetherCalculator(dimensionAware);
            modGui = new SaveCoordinatesGui(fileStore, playerLocator, dimensionAware, keyBinds, guiController,
                    netherCalculator);

            pingPositionOperation = new CurrentPositionPingOperation(fileStore, () -> playerLocator.locate());
        }
    }
}
