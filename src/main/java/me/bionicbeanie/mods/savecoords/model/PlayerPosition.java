package me.bionicbeanie.mods.savecoords.model;

public class PlayerPosition extends PlayerRawPosition {

    private String id, locationName;
    private PositionMetadata positionMetadata;

    public PlayerPosition(String id, PlayerRawPosition rawPosition, String locationName,
            PositionMetadata positionMetadata) {
        super(rawPosition.getX(), rawPosition.getY(), rawPosition.getZ(), rawPosition.getWorldDimension());
        this.id = id;
        this.positionMetadata = positionMetadata;
        this.locationName = locationName;
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

    @Override
    public String toString() {
        return this.locationName + " in [ " + this.getWorldDimension() + " ] at [ " + this.getX() + ", " + this.getY()
                + ", " + this.getZ() + " ]";
    }
}
