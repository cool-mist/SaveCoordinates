package me.bionicbeanie.mods.savecoords.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.bionicbeanie.mods.savecoords.IDimensionAware;
import me.bionicbeanie.mods.savecoords.util.ResourceUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

class DimensionAware implements IDimensionAware {

    private List<Dimension> allDimensions;
    private List<IDimension> allDimensionsReadOnly;
    private IDimension UNKNOWN = new Dimension("Unknown", ResourceUtils.getIdentifier("unknown"), 0);
    private MinecraftClient minecraftClient;
    private int DIM_OVERWORLD = 1;
    private int DIM_NETHER = 2;
    private int DIM_END = 4;

    public DimensionAware(MinecraftClient minecraftClient) {
        this.minecraftClient = minecraftClient;
        allDimensions = new ArrayList<>();

        initialize(allDimensions);

        allDimensionsReadOnly = Collections.unmodifiableList(allDimensions);
    }

    // TODO: Provide a hook for other mods to add in their dimension info
    private void initialize(List<Dimension> dimensions) {
        dimensions.add(new Dimension("Overworld", ResourceUtils.getIdentifier("overworld"), DIM_OVERWORLD));
        dimensions.add(new Dimension("Nether", ResourceUtils.getIdentifier("nether"), DIM_NETHER));
        dimensions.add(new Dimension("End", ResourceUtils.getIdentifier("end"), DIM_END));
    }

    @Override
    public List<IDimension> getDimensions() {
        return allDimensionsReadOnly;
    }

    @Override
    public IDimension getCurrentDimension() {
        String dimensionKey = minecraftClient.player.getEntityWorld().getRegistryKey().getValue().toString();
        return getDimension(dimensionKey);
    }
    
    @Override
    public IDimension getDimension(String dimensionKey) {
        for (Dimension dimension : allDimensions) {
            if (dimension.isDimension(dimensionKey)) {
                return dimension;
            }
        }
        
        return UNKNOWN;
    }
    
    @Override
    public boolean isOverworld(IDimension dimension) {
        return dimension.getId() == DIM_OVERWORLD;
    }

    @Override
    public boolean isNether(IDimension dimension) {
        return dimension.getId() == DIM_NETHER;
    }

    @Override
    public boolean isEnd(IDimension dimension) {
        return dimension.getId() == DIM_END;
    }
    
    static class Dimension implements IDimension {

        private String name;
        private Identifier spriteIdentifier;
        private int dimensionId;

        Dimension(String name, Identifier spriteIdentifier, int dimensionId) {
            this.name = name;
            this.spriteIdentifier = spriteIdentifier;
            this.dimensionId = dimensionId;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Identifier getSpriteIdentifier() {
            return spriteIdentifier;
        }

        boolean isDimension(String registryKey) {
            return (registryKey != null && registryKey.toLowerCase().contains(this.name.toLowerCase()));
        }
        
        int getDimensionId() {
           return this.dimensionId; 
        }

        @Override
        public int getId() {
            return this.dimensionId;
        }
    }

}
