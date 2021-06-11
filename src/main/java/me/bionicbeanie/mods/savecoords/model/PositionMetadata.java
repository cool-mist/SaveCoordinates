package me.bionicbeanie.mods.savecoords.model;

public class PositionMetadata {

    private String worldName, notes;
    private long createdMillis, lastModifiedMillis;

    public PositionMetadata(String worldName, String notes) {
        this.worldName = worldName;
        this.notes = notes;
        this.createdMillis = System.currentTimeMillis();
        this.lastModifiedMillis = this.createdMillis;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getCreatedMillis() {
        return createdMillis;
    }

    public long getLastModifiedMillis() {
        return lastModifiedMillis;
    }

    public void updateLastModified() {
        this.lastModifiedMillis = System.currentTimeMillis();
    }
}
