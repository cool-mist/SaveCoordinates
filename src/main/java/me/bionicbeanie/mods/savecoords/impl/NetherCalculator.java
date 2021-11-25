package me.bionicbeanie.mods.savecoords.impl;

import me.bionicbeanie.mods.savecoords.IDimensionAware;
import me.bionicbeanie.mods.savecoords.IDimensionAware.IDimension;
import me.bionicbeanie.mods.savecoords.INetherCalculator;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;

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
    public PlayerRawPosition convert(PlayerRawPosition position) {
        IDimension dimension = dimensionAware.getDimension(position.getWorldDimension());

        if (dimensionAware.isOverworld(dimension)) {
            return new PlayerRawPosition(position.getX() / MULTIPLIER, position.getY(), position.getZ() / MULTIPLIER,
                    nether.getName());
        }

        if (dimensionAware.isNether(dimension)) {
            return new PlayerRawPosition(position.getX() * MULTIPLIER, position.getY(), position.getZ() * MULTIPLIER,
                    overworld.getName());
        }

        return position;
    }

}
