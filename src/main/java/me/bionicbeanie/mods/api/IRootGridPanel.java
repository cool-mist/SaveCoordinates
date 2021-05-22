package me.bionicbeanie.mods.api;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.WWidget;

public interface IRootGridPanel {

    public void add(WWidget widget, int x, int y, int w, int h);
    
    public void validate(GuiDescription gui);
    
    public void reset();
}
