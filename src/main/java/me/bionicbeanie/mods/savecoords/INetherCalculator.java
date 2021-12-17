package me.bionicbeanie.mods.savecoords;

import me.bionicbeanie.mods.savecoords.model.PlayerPosition;

public interface INetherCalculator {
    PlayerPosition convert(PlayerPosition position);
}
