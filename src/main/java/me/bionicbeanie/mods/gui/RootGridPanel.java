package me.bionicbeanie.mods.gui;

import java.util.ArrayList;
import java.util.List;

import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.api.IRootGridPanel;

public class RootGridPanel extends WGridPanel implements IRootGridPanel{

    private List<WWidget> children;
    
    public RootGridPanel() {
        this(18);
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

}
