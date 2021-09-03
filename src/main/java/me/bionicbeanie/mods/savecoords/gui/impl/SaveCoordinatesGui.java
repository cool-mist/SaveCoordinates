package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.List;

import me.bionicbeanie.mods.savecoords.IDimensionAware;
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
    private IDimensionAware dimensionAware;
    private IKeyBinds keyBinds;

    SaveCoordinatesGui(IFileStore fileStore, IPlayerLocator locator, IDimensionAware dimensionAware, IKeyBinds binds,
            IGuiController screenController) {
        this.screenController = screenController;
        this.fileStore = fileStore;
        this.keyBinds = binds;
        this.locator = locator;
        this.dimensionAware = dimensionAware;

        this.defaultHandler = CreateDefaultViewHandler();
        this.listHandler = CreateListViewHandler();
        this.configHandler = CreateConfigHandler();
    }

    @Override
    public void open() {
        showDefaultView(null);
    }

    private IViewHandler<PlayerPosition> CreateDefaultViewHandler() {
        DefaultViewHandler handler = new DefaultViewHandler(fileStore, locator, dimensionAware);

        handler.onSaveButtonClick(this::savePosition);
        handler.onListButtonClick(this::showListView);
        handler.onConfigButtonClick(this::showConfigView);
        handler.onPingButtonClick(this::pingPosition);
        handler.onCloseButtonClick(screenController::closeScreen);

        return handler;
    }

    private IViewHandler<Void> CreateListViewHandler() {
        ListViewHandler handler = new ListViewHandler(fileStore, this::onDeletePosition, this::onEditPosition,
                this::pingPosition);

        handler.onBackButtonClick(() -> showDefaultView(null));

        return handler;
    }

    private IViewHandler<List<IKeyBinding>> CreateConfigHandler() {
        ConfigViewHandler handler = new ConfigViewHandler();

        handler.onBackButtonClick(() -> showDefaultView(null));
        handler.onSaveButtonClick(this::saveConfigs);

        return handler;
    }

    private void savePosition() {
        ErrorResponse response = new SavePositionOperation(fileStore, defaultHandler::getState).call();
        if (!response.isFailed()) {
            showListView();
        }
    }

    private void onDeletePosition(PlayerPosition position) {
        new DeletePositionOperation(fileStore, position::getId).call();
        showListView();
    }

    private void onEditPosition(PlayerPosition position) {
        showDefaultView(position);
    }

    private void pingPosition() {
        new PingPositionOperation(fileStore, locator::locate).call();
    }

    private void pingPosition(PlayerRawPosition position) {
        new PingPositionOperation(fileStore, () -> position).call();
    }

    private void saveConfigs() {
        new SaveConfigsOperation(keyBinds, fileStore, configHandler::getState).call();
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

    private Screen createConfigScreen() {
        return this.configHandler.createView(keyBinds.getAllBinds());
    }
}
