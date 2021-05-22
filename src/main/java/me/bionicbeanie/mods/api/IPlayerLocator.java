package me.bionicbeanie.mods.api;

import me.bionicbeanie.mods.model.PlayerRawPosition;
import net.minecraft.client.MinecraftClient;

public interface IPlayerLocator {

    public PlayerRawPosition locate(MinecraftClient client);
}
