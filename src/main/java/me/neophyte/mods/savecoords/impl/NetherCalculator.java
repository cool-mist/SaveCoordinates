package me.neophyte.mods.savecoords.impl;

import me.neophyte.mods.savecoords.IDimensionAware;
import me.neophyte.mods.savecoords.INetherCalculator;
import me.neophyte.mods.savecoords.IDimensionAware.IDimension;
import me.neophyte.mods.savecoords.model.PlayerPosition;
import me.neophyte.mods.savecoords.model.PlayerRawPosition;

class NetherCalculator implements INetherCalculator {

    private int MULTIPLIER = 8;
    private IDimensionAware dimensionAware;
    private IDimension overworld;
    private IDimension nether;

    public NetherCalculator(IDimensionAware dimensionAware) {
        this.dimensionAware = dimensionAware;

        for (IDimension dimension : dimensionAware.getDimensions()) {
            if (dimensionAware.isOverworld(dimension)) {
                overworld = dimension;
                continue;
            }

            if (dimensionAware.isNether(dimension)) {
                nether = dimension;
            }
        }
    }

    @Override
    public PlayerPosition convert(PlayerPosition position) {
        IDimension dimension = dimensionAware.getDimension(position.getWorldDimension());
        PlayerRawPosition rawPosition = null;
        
        if (dimensionAware.isOverworld(dimension)) {
            rawPosition =  new PlayerRawPosition(position.getX() / MULTIPLIER, position.getY(), position.getZ() / MULTIPLIER,
                    nether.getName());
        }

        if (dimensionAware.isNether(dimension)) {
            rawPosition = new PlayerRawPosition(position.getX() * MULTIPLIER, position.getY(), position.getZ() * MULTIPLIER,
                    overworld.getName());
        }

        if(rawPosition != null) {
        	return new PlayerPosition(position.getId(), rawPosition, position.getLocationName(), position.getPositionMetadata());
        }
        
        return position;
    }

}
