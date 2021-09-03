package me.bionicbeanie.mods.savecoords;

import java.util.List;

import net.minecraft.util.Identifier;

public interface IDimensionAware {

    List<IDimension> getDimensions();
    
    IDimension getCurrentDimension();
    
    IDimension getDimension(String dimensionKey);

    public interface IDimension {

        public String getName();

        public Identifier getSpriteIdentifier();
    }
}
