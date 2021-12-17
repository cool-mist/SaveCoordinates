package me.neophyte.mods.savecoords;

import me.neophyte.mods.savecoords.model.PlayerRawPosition;

public interface IPlayerLocator {
    PlayerRawPosition locate();
}
