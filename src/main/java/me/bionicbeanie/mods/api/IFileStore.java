package me.bionicbeanie.mods.api;

import java.io.IOException;
import java.util.List;

import me.bionicbeanie.mods.model.PlayerPosition;

public interface IFileStore {

    public String getDefaultWorld() throws IOException;
    
    public void setDefaultWorld(String defaultWorldName) throws IOException;

    public void save(PlayerPosition position) throws IOException;

    public void delete(String id) throws IOException;

    public List<PlayerPosition> list() throws IOException;
}
