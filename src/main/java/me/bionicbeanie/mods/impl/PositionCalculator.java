package me.bionicbeanie.mods.impl;

import me.bionicbeanie.mods.api.IPositionCalculator;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class PositionCalculator implements IPositionCalculator {

    public PlayerRawPosition locate(MinecraftClient client) {
        Vec3d pos = client.player.getPos();
        long x = Math.round(pos.x);
        long y = Math.round(pos.y);
        long z = Math.round(pos.z);
        String worldDimension = client.player.getEntityWorld().getRegistryKey().getValue().toString();

        return new PlayerRawPosition(x, y, z, worldDimension);
    }
}
