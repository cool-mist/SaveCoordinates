package me.neophyte.mods.savecoords.model;

public class ModData {

    private String defaultWorldName;
    
    private PlayerPosition[] positions;
    
    private ConfigData configData;

    public String getDefaultWorldName() {
        return defaultWorldName;
    }

    public void setDefaultWorldName(String defaultWorldName) {
        this.defaultWorldName = defaultWorldName;
    }

    public PlayerPosition[] getPositions() {
        return positions;
    }

    public void setPositions(PlayerPosition[] positions) {
        this.positions = positions;
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public void setConfigData(ConfigData configData) {
        this.configData = configData;
    }
}
