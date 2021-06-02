package me.bionicbeanie.mods.savecoords.model;

public class ModData {

    private String defaultWorldName = "";
    
    private PlayerPosition[] positions;

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
}
