package me.bionicbeanie.mods.savecoords.gui.impl;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.gui.IGuiController;
import me.bionicbeanie.mods.savecoords.gui.IViewHandler;
import me.bionicbeanie.mods.savecoords.model.PlayerPosition;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;

public class SaveCoordinatesGui {

    private IGuiController screenController;
    private IFileStore fileStore;
    private IViewHandler<PlayerPosition> defaultHandler;
    private IViewHandler<Void> listHandler;
    private IPlayerLocator locator;

    SaveCoordinatesGui(IFileStore fileStore, IPlayerLocator locator, IGuiController screenController) {
        this.screenController = screenController;
        this.fileStore = fileStore;
        this.locator = locator;

        this.defaultHandler = CreateDefaultViewHandler();
        this.listHandler = CreateListViewHandler();

        showDefaultView(null);
    }

    private IViewHandler<PlayerPosition> CreateDefaultViewHandler() {
        DefaultViewHandler handler = new DefaultViewHandler(fileStore, locator);

        handler.onClose(screenController::closeScreen);
        handler.onSave(this::onSavePosition);
        handler.onList(this::showListView);
        handler.onPing(new PingOperation(fileStore, locator::locate));

        return handler;
    }

    private IViewHandler<Void> CreateListViewHandler() {
        ListViewHandler handler = new ListViewHandler(fileStore, this::onDeletePosition, this::onEditPosition,
                this::onPingPosition);
        
        handler.onBack(() -> showDefaultView(null));

        return handler;
    }

    private void onSavePosition() {
        new SaveOperation(fileStore, defaultHandler::getState).run();
        showListView();
    }

    private void onDeletePosition(PlayerPosition position) {
        new DeleteOperation(fileStore, () -> position.getId()).run();
        showListView();
    }

    private void onEditPosition(PlayerPosition position) {
        showDefaultView(position);
    }

    private void onPingPosition(PlayerRawPosition position) {
        new PingOperation(fileStore, () -> position).run();
    }
    
    private void showDefaultView(PlayerPosition position) {
        screenController.openScreen(this.defaultHandler.createView(position));
    }

    private void showListView() {
        screenController.openScreen(this.listHandler.createView(null));
    }

//    public void showConfigView() {
//        
//        ConfigBuilder builder = ConfigBuilder.create()
//            .setParentScreen(this.screen)
//            .setTitle(new LiteralText("Save Coordinates config"));
//        
//        ConfigCategory general = builder.getOrCreateCategory(new LiteralText("Generic"));
//        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
//        
//        general.addEntry(entryBuilder.startKeyCodeField(new LiteralText("Default Keybind"), 
//                InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_H))
//                .setDefaultValue(InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_H))
//                .setTooltip(new LiteralText("Keybind to open Save Coordinates menu"))
//                .setSaveConsumer(newValue -> newValue = newValue)
//                .build());
//
//        screenController.openScreen(builder.build());
//    }

}
