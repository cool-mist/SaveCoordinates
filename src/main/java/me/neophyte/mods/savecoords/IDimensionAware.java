package me.neophyte.mods.savecoords;

import java.util.List;

import net.minecraft.util.Identifier;

public interface IDimensionAware {

    List<IDimension> getDimensions();
    
    IDimension getCurrentDimension();
    
    IDimension getDimension(String dimensionKey);
    
    boolean isOverworld(IDimension dimension);
    
    boolean isNether(IDimension dimension);
    
    boolean isEnd(IDimension dimension);

    public interface IDimension {

        public int getId();
        
        public String getName();

        public Identifier getSpriteIdentifier();
    }
}
