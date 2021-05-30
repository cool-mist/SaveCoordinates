package me.bionicbeanie.mods.savecoords;

import java.io.IOException;
import java.util.List;

import me.bionicbeanie.mods.savecoords.model.PlayerPosition;

public interface IFileStore {
    String getDefaultWorld() throws IOException;

    void setDefaultWorld(String defaultWorldName) throws IOException;

    void save(PlayerPosition position) throws IOException;

    void delete(String id) throws IOException;

    List<PlayerPosition> list() throws IOException;
}
