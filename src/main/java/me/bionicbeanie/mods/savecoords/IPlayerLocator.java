package me.bionicbeanie.mods.savecoords;

import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;

public interface IPlayerLocator {
    PlayerRawPosition locate();
}
