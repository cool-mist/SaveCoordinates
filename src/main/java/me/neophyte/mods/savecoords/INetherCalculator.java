package me.neophyte.mods.savecoords;

import me.neophyte.mods.savecoords.model.PlayerPosition;

public interface INetherCalculator {
    PlayerPosition convert(PlayerPosition position);
}
