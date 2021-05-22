package me.bionicbeanie.mods.model;

public class PlayerPosition {

    private PlayerRawPosition rawPosition;
    private String locationName;

    public PlayerPosition(PlayerRawPosition rawPosition, String locationName) {
        this.rawPosition = rawPosition;
        this.locationName = locationName;
    }

    public long getX() {
        return rawPosition.getX();
    }

    public long getY() {
        return rawPosition.getY();
    }

    public long getZ() {
        return rawPosition.getZ();
    }

    public String getWorldDimension() {
        return rawPosition.getWorldDimension();
    }

    public String getLocationName() {
        return locationName;
    }
}
