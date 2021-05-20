package me.bionicbeanie.mods.impl;

import java.io.IOException;

import me.bionicbeanie.mods.api.ICoordinateSaveHandler;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.model.PlayerPosition;

public class CoordinateSaveHandler implements ICoordinateSaveHandler{

    private IFileStore fileStore;

    public CoordinateSaveHandler(IFileStore fileStore) {
        this.fileStore = fileStore;
    }
    
    @Override
    public void save(PlayerPosition position) {
        try {
            this.fileStore.save(position);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
