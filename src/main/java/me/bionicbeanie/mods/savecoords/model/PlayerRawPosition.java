package me.bionicbeanie.mods.savecoords.model;

public class PlayerRawPosition {
    private long x, y, z;
    private String worldDimension;

    public PlayerRawPosition(long x, long y, long z, String worldDimension) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldDimension = worldDimension;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public String getWorldDimension() {
        return worldDimension;
    }

    @Override
    public String toString() {
        return "I'm in [ " + worldDimension + " ] at [ " + x + ", " + y + ", " + z + " ]";
    }

}
