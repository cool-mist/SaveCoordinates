package me.bionicbeanie.mods.savecoords.gui;

import net.minecraft.client.gui.screen.Screen;

public interface IViewHandler<T> {
    Screen createView(T state);

    T getState();
}
