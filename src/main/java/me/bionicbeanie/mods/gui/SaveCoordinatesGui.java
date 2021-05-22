package me.bionicbeanie.mods.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.api.IGuiHandler;
import me.bionicbeanie.mods.api.IRootGridPanel;
import me.bionicbeanie.mods.api.IScreenController;

public class SaveCoordinatesGui extends LightweightGuiDescription implements IGui {

    private IRootGridPanel rootGridPanel;
    private IGuiHandler saveHandler;
    private IGuiHandler listHandler;
    private IScreenController screenController;

    @Override
    public void init(IGuiHandler saveHandler, IGuiHandler listHandler, IScreenController screenController) {
        this.rootGridPanel = createRootPanel();
        this.saveHandler = saveHandler;
        this.listHandler = listHandler;
        this.screenController = screenController;

        openScreen();
    }

    @Override
    public void showDefaultView() {
        showView(saveHandler);
    }

    @Override
    public void showListView() {
        showView(listHandler);
    }

    @Override
    public void close() {
        screenController.closeScreen();
    }

    private void openScreen() {
        screenController.openScreen(new SaveCoordinatesScreen(this));
    }

    private IRootGridPanel createRootPanel() {
        RootGridPanel panel = new RootGridPanel(18);
        panel.setSize(7 * 18, 7 * 18);

        setRootPanel(panel);
        return panel;
    }

    private void showView(IGuiHandler handler) {
        rootGridPanel.reset();
        handler.placeWidgets(rootGridPanel);
        rootGridPanel.validate(this);
    }
}
