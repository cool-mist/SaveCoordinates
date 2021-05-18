package me.bionicbeanie.mods.impl;

import me.bionicbeanie.mods.core.IPositionCalculator;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class PositionCalculator implements IPositionCalculator{

    public PlayerRawPosition locate(ClientPlayerEntity player) {
        Vec3d pos = player.getPos();
        long x = Math.round(pos.x);
        long y = Math.round(pos.y);
        long z = Math.round(pos.z);
        String worldDimension = player.world.getRegistryKey().getValue().toString();
    
        return new PlayerRawPosition(x, y, z, worldDimension);
    }
}
