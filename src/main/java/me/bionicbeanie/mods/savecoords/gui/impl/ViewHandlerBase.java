package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import me.bionicbeanie.mods.savecoords.gui.IRootPanel;
import me.bionicbeanie.mods.savecoords.gui.IViewHandler;
import me.bionicbeanie.mods.savecoords.gui.SaveCoordinatesScreen;
import net.minecraft.client.gui.screen.Screen;

abstract class ViewHandlerBase<T> extends LightweightGuiDescription implements IViewHandler<T> {

    private IRootPanel rootGridPanel;
    private Supplier<T> stateSupplier;

    protected ViewHandlerBase() {
        this.rootGridPanel = createRootPanel();
    }

    protected abstract Supplier<T> placeWidgets(IRootPanel rootGridPanel, T state);

    @Override
    public Screen createView(T state) {
        rootGridPanel.reset();
        this.stateSupplier = placeWidgets(rootGridPanel, state);
        rootGridPanel.validate();
        return new SaveCoordinatesScreen(this);
    }
    
    @Override
    public T getState() {
        return stateSupplier.get();
    }

    private IRootPanel createRootPanel() {
        RootGridPanel panel = new RootGridPanel(this);
        panel.setSize(15 * 18, 10 * 18);

        setRootPanel(panel);
        return panel;
    }
}
