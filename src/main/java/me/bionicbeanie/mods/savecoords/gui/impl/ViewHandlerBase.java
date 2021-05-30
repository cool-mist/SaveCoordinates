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

    protected abstract Supplier<T> setupView(IRootPanel rootGridPanel, T state);

    protected Screen createScreen() {
        return new SaveCoordinatesScreen(this);
    }

    @Override
    public Screen createView(T state) {
        rootGridPanel.reset();
        this.stateSupplier = setupView(rootGridPanel, state);
        rootGridPanel.validate();

        return createScreen();
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
