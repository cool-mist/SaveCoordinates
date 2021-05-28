package me.bionicbeanie.mods.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.api.IViewHandler;
import me.bionicbeanie.mods.model.PlayerPosition;
import me.bionicbeanie.mods.api.IRootGridPanel;
import me.bionicbeanie.mods.api.IScreenController;

public class SaveCoordinatesGui extends LightweightGuiDescription implements IGui {

    private IRootGridPanel rootGridPanel;
    private IViewHandler<PlayerPosition> defaultHandler;
    private IViewHandler<Void> listHandler;
    private IScreenController screenController;

    @Override
    public void init(IViewHandler<PlayerPosition> saveHandler, IViewHandler<Void> listHandler,
            IScreenController screenController) {
        this.rootGridPanel = createRootPanel();
        this.defaultHandler = saveHandler;
        this.listHandler = listHandler;
        this.screenController = screenController;

        openScreen();
    }

    @Override
    public void showDefaultView() {
        showView(defaultHandler);
    }

    @Override
    public void setDefaultViewState(PlayerPosition position) {
        defaultHandler.setState(position);
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
        panel.setSize(15 * 18, 10 * 18);

        setRootPanel(panel);
        return panel;
    }

    private void showView(IViewHandler<?> handler) {
        rootGridPanel.reset();
        handler.placeWidgets(rootGridPanel);
        rootGridPanel.validate(this);
    }

}
