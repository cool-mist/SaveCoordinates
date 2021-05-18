package me.bionicbeanie.mods.core;

import java.io.IOException;
import java.util.List;

import me.bionicbeanie.mods.model.PlayerPosition;

public interface IFileStore {

    public void save(List<PlayerPosition> positions) throws IOException;
    
    public void save(PlayerPosition position) throws IOException;
}
