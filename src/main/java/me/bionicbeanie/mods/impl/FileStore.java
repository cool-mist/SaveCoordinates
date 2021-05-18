package me.bionicbeanie.mods.impl;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.bionicbeanie.mods.core.IFileStore;
import me.bionicbeanie.mods.model.PlayerPosition;

public class FileStore implements IFileStore {

    private static String DEFAULT_DIR = "saveCoordinates";
    private static String DEFAULT_FILE = "coordiates.json";

    private Path saveFilePath;
    private Gson gson;

    public FileStore(String baseDir) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.saveFilePath = Path.of(baseDir, DEFAULT_DIR, DEFAULT_FILE);
        
        try {
            Files.createDirectories(Path.of(baseDir, DEFAULT_DIR));
            Files.createFile(this.saveFilePath);
        }catch(FileAlreadyExistsException e) {
            //ignore
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(List<PlayerPosition> positions) {
        // TODO Auto-generated method stub

    }

    @Override
    public void save(PlayerPosition position) throws IOException {
        List<String> lines = Files.readAllLines(saveFilePath);
        PlayerPosition[] players = gson.fromJson(String.join("", lines), PlayerPosition[].class);
        
        List<PlayerPosition> playersList = new LinkedList<>();
        
        if(players != null) {
            for(int i = 0; i < players.length; ++i) {
                playersList.add(players[i]);
            }
        }
        
        playersList.add(position);
        
        String serialized = gson.toJson(playersList.toArray());
        Files.writeString(saveFilePath, serialized, StandardOpenOption.WRITE);
    }

}
