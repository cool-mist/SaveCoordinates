package me.bionicbeanie.mods.core;

import me.bionicbeanie.mods.model.PlayerRawPosition;
import net.minecraft.client.network.ClientPlayerEntity;

public interface IPositionCalculator {

    public PlayerRawPosition locate(ClientPlayerEntity player);
}
