package me.bionicbeanie.mods.savecoords.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.bionicbeanie.mods.savecoords.IDimensionAware;
import me.bionicbeanie.mods.savecoords.util.ResourceUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class DimensionAware implements IDimensionAware {

    private List<Dimension> allDimensions;
    private List<IDimension> allDimensionsReadOnly;
    private IDimension UNKNOWN = new Dimension("Unknown", ResourceUtils.getIdentifier("unknown"));
    private MinecraftClient minecraftClient;

    public DimensionAware(MinecraftClient minecraftClient) {
        this.minecraftClient = minecraftClient;
        allDimensions = new ArrayList<>();

        initialize(allDimensions);

        allDimensionsReadOnly = Collections.unmodifiableList(allDimensions);
    }

    // TODO: Provide a hook for other mods to add in their dimension info
    private void initialize(List<Dimension> dimensions) {
        dimensions.add(new Dimension("Overworld", ResourceUtils.getIdentifier("overworld")));
        dimensions.add(new Dimension("Nether", ResourceUtils.getIdentifier("nether")));
        dimensions.add(new Dimension("End", ResourceUtils.getIdentifier("end")));
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

    static class Dimension implements IDimension {

        private String name;
        private Identifier spriteIdentifier;

        Dimension(String name, Identifier spriteIdentifier) {
            this.name = name;
            this.spriteIdentifier = spriteIdentifier;
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
    }

    
}
