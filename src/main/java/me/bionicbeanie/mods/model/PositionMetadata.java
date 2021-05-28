package me.bionicbeanie.mods.model;

import java.time.LocalDateTime;

public class PositionMetadata {

    private String worldName, notes;
    private LocalDateTime created, lastModified;

    public PositionMetadata(String worldName, String notes) {
        this.worldName = worldName;
        this.notes = notes;
        this.created = LocalDateTime.now();
        this.lastModified = this.created;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }
}
