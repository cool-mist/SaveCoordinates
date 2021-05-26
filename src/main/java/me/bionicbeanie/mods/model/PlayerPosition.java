package me.bionicbeanie.mods.model;

public class PlayerPosition {

    private PlayerRawPosition rawPosition;
    private String id, locationName;
    private PositionMetadata positionMetadata;

    public PlayerPosition(String id, PlayerRawPosition rawPosition, String locationName,
            PositionMetadata positionMetadata) {
        this.id = id;
        this.rawPosition = rawPosition;
        this.positionMetadata = positionMetadata;
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

    public PositionMetadata getPositionMetadata() {
        return positionMetadata;
    }

    public String getId() {
        return id;
    }
}
