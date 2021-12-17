package me.bionicbeanie.mods.savecoords.impl;

import me.bionicbeanie.mods.savecoords.IDimensionAware;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

class PlayerLocator implements IPlayerLocator {

    private MinecraftClient client;
	private IDimensionAware dimensionAware;

    public PlayerLocator(MinecraftClient client, IDimensionAware dimensionAware) {
        this.client = client;
        this.dimensionAware = dimensionAware;
    }

    public PlayerRawPosition locate() {
        Vec3d pos = client.player.getPos();
        long x = Math.round(pos.x);
        long y = Math.round(pos.y);
        long z = Math.round(pos.z);
        
        String worldDimension = this.dimensionAware.getCurrentDimension().getName();

        return new PlayerRawPosition(x, y, z, worldDimension);
    }
}
