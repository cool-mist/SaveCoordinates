package me.bionicbeanie.mods.model;

import java.util.Date;

public class PositionMetadata {

    private String worldName, notes;
    private Date created, lastModified;
    
    public String getWorldName() {
        return worldName;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public Date getCreated() {
        return created;
    }
    
    public Date getLastModified() {
        return lastModified;
    }
}
