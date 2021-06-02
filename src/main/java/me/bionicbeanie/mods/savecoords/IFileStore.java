package me.bionicbeanie.mods.savecoords;

import java.io.IOException;
import java.util.List;

import me.bionicbeanie.mods.savecoords.model.PlayerPosition;

public interface IFileStore {
    String readDefaultWorldName() throws IOException;

    void writeDefaultWorldName(String defaultWorldName) throws IOException;

    void writePosition(PlayerPosition position) throws IOException;

    void deletePosition(String id) throws IOException;

    List<PlayerPosition> listPositions() throws IOException;
}
