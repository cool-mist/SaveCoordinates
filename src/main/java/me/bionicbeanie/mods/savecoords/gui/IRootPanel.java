package me.bionicbeanie.mods.savecoords.gui;

import io.github.cottonmc.cotton.gui.widget.WWidget;

public interface IRootPanel {
    void add(WWidget widget, int x, int y, int w, int h);

    void validate();

    void reset();
}
