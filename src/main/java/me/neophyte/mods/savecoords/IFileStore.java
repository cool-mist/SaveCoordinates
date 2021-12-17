package me.neophyte.mods.savecoords;

import java.io.IOException;
import java.util.List;

import me.neophyte.mods.savecoords.model.ConfigData;
import me.neophyte.mods.savecoords.model.PlayerPosition;

public interface IFileStore {
    String readDefaultWorldName() throws IOException;
    
    ConfigData readConfigData() throws IOException;

    void writeDefaultWorldName(String defaultWorldName) throws IOException;

    void writePosition(PlayerPosition position) throws IOException;
    
    void writeConfigs(ConfigData configData) throws IOException;

    void deletePosition(String id) throws IOException;

    List<PlayerPosition> listPositions() throws IOException;
}
