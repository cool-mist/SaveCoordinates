package me.bionicbeanie.mods.savecoords.impl;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import net.minecraft.client.MinecraftClient;

public class Factory {

    public static IFileStore createFileStore(String baseDirectory) {
        return new FileStore(baseDirectory);
    }
    
    public static IPlayerLocator CreatePlayerLocator(MinecraftClient client) {
        return new PlayerLocator(client);
    }
}
