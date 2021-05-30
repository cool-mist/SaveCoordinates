package me.bionicbeanie.mods.savecoords.impl;

import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

class PlayerLocator implements IPlayerLocator {

    private MinecraftClient client;

    public PlayerLocator(MinecraftClient client) {
        this.client = client;
    }

    public PlayerRawPosition locate() {
        Vec3d pos = client.player.getPos();
        long x = Math.round(pos.x);
        long y = Math.round(pos.y);
        long z = Math.round(pos.z);
        String worldDimension = client.player.getEntityWorld().getRegistryKey().getValue().toString();

        return new PlayerRawPosition(x, y, z, worldDimension);
    }
}
