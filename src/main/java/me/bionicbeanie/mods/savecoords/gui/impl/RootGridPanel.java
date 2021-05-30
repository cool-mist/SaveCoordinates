package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.ArrayList;
import java.util.List;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.savecoords.gui.IRootPanel;

class RootGridPanel extends WGridPanel implements IRootPanel{

    private List<WWidget> children;
    private GuiDescription guiDescription;
    
    public RootGridPanel(GuiDescription guiDescription) {
        this(18);
        
        this.guiDescription = guiDescription;
    }
    
    public RootGridPanel(int gridSize){
        super(gridSize);
        children = new ArrayList<>();
    }
    
    @Override
    public void add(WWidget widget, int x, int y, int w, int h) {
        super.add(widget, x, y, w, h);
        this.children.add(widget);
    }

    @Override
    public void reset() {
        children.forEach(this::remove);
        children.clear();
    }

    @Override
    public void validate() {
        validate(guiDescription);
    }
}
