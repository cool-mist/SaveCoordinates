package me.bionicbeanie.mods.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import me.bionicbeanie.mods.api.ICoordinateListHandler;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.model.PlayerPosition;

public class CoordinateListHandler implements ICoordinateListHandler{

    private IFileStore fileStore;
    
    public CoordinateListHandler(IFileStore fileStore) {
        this.fileStore = fileStore;
    }
    
    @Override
    public List<PlayerPosition> list() {
        
        try {
            return fileStore.list();
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

}
