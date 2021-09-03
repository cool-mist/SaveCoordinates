package me.bionicbeanie.mods.savecoords.gui.impl;

import io.github.cottonmc.cotton.gui.widget.WSprite;
import me.bionicbeanie.mods.savecoords.util.ResourceUtils;
import net.minecraft.util.Identifier;

public class DimensionSprite extends WSprite {
    
    public DimensionSprite() {
        super(ResourceUtils.getIdentifier("unknown"));
    }

    public void setDimension(String dimensionKey) {
        Identifier identifier = ResourceUtils.getIdentifier(dimensionKey);
        this.setImage(identifier);
    }
}
