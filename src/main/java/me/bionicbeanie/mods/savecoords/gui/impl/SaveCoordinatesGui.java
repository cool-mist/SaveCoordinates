package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.List;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IKeyBinds;
import me.bionicbeanie.mods.savecoords.IKeyBinds.IKeyBinding;
import me.bionicbeanie.mods.savecoords.IModGui;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.gui.IGuiController;
import me.bionicbeanie.mods.savecoords.gui.IViewHandler;
import me.bionicbeanie.mods.savecoords.model.PlayerPosition;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import net.minecraft.client.gui.screen.Screen;

public class SaveCoordinatesGui implements IModGui {

    private IGuiController screenController;
    private IFileStore fileStore;
    private IViewHandler<PlayerPosition> defaultHandler;
    private IViewHandler<Void> listHandler;
    private IViewHandler<List<IKeyBinding>> configHandler;
    private IPlayerLocator locator;
    private IKeyBinds keyBinds;

    SaveCoordinatesGui(IFileStore fileStore, IPlayerLocator locator, IKeyBinds binds, IGuiController screenController) {
        this.screenController = screenController;
        this.fileStore = fileStore;
        this.keyBinds = binds;
        this.locator = locator;

        this.defaultHandler = CreateDefaultViewHandler();
        this.listHandler = CreateListViewHandler();
        this.configHandler = CreateConfigHandler();

    }

    @Override
    public void open() {
        showDefaultView(null);
    }

    private Screen createConfigScreen() {
        return this.configHandler.createView(keyBinds.getAllBinds());
    }

    private IViewHandler<PlayerPosition> CreateDefaultViewHandler() {
        DefaultViewHandler handler = new DefaultViewHandler(fileStore, locator);

        handler.onSave(this::onSavePosition);
        handler.onList(this::showListView);
        handler.onConfig(this::showConfigView);
        handler.onPing(new PingPositionOperation(fileStore, locator::locate));
        handler.onClose(screenController::closeScreen);

        return handler;
    }

    private IViewHandler<Void> CreateListViewHandler() {
        ListViewHandler handler = new ListViewHandler(fileStore, this::onDeletePosition, this::onEditPosition,
                this::onPingPosition);

        handler.onBack(() -> showDefaultView(null));

        return handler;
    }

    private IViewHandler<List<IKeyBinding>> CreateConfigHandler() {
        ConfigViewHandler handler = new ConfigViewHandler();

        handler.onBack(() -> showDefaultView(null));
        handler.onSave(this::onSaveConfigs);

        return handler;
    }

    private void onSavePosition() {
        new SavePositionOperation(fileStore, defaultHandler::getState).run();
        showListView();
    }

    private void onDeletePosition(PlayerPosition position) {
        new DeletePositionOperation(fileStore, () -> position.getId()).run();
        showListView();
    }

    private void onEditPosition(PlayerPosition position) {
        showDefaultView(position);
    }

    private void onPingPosition(PlayerRawPosition position) {
        new PingPositionOperation(fileStore, () -> position).run();
    }

    private void onSaveConfigs() {
        new SaveConfigsOperation(keyBinds, fileStore, configHandler::getState).run();
        showDefaultView(null);
    }

    private void showDefaultView(PlayerPosition position) {
        screenController.openScreen(this.defaultHandler.createView(position));
    }

    private void showListView() {
        screenController.openScreen(this.listHandler.createView(null));
    }

    private void showConfigView() {
        screenController.openScreen(createConfigScreen());
    }

}
