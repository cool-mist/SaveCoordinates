package me.bionicbeanie.mods.savecoords;

import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;

public interface INetherCalculator {
    PlayerRawPosition convert(PlayerRawPosition position);
}
