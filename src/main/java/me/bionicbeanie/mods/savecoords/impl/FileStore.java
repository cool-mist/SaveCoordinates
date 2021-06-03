package me.bionicbeanie.mods.savecoords.impl;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.model.ConfigData;
import me.bionicbeanie.mods.savecoords.model.ModData;
import me.bionicbeanie.mods.savecoords.model.PlayerPosition;

class FileStore implements IFileStore {

    private static String DEFAULT_DIR = "saveCoordinates";
    private static String DEFAULT_FILE = "coordiates.json";

    private Path saveFilePath;
    private Gson gson;

    public FileStore(String baseDir) {
        this.gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        this.saveFilePath = Paths.get(baseDir, DEFAULT_DIR, DEFAULT_FILE);

        try {
            Files.createDirectories(Paths.get(baseDir, DEFAULT_DIR));
            Files.createFile(this.saveFilePath);
        } catch (FileAlreadyExistsException e) {
            // ignore
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readDefaultWorldName() throws IOException {
        ModData data = load();
        
        return data.getDefaultWorldName();
    }
    
    @Override
    public ConfigData readConfigData() throws IOException {
        ModData data = load();
        
        return data.getConfigData();
    }


    @Override
    public void writeDefaultWorldName(String defaultWorldName) throws IOException {
        ModData data = load();
        data.setDefaultWorldName(defaultWorldName);;

        dump(data);
    }
    
    @Override
    public void writeConfigs(ConfigData configData) throws IOException {
        ModData data = load();
        data.setConfigData(configData);
        
        dump(data);
    }

    @Override
    public List<PlayerPosition> listPositions() throws IOException {
        ModData data = load();
        List<PlayerPosition> playerPositionList = new LinkedList<>();

        if (data.getPositions() != null) {
            for (int i = 0; i < data.getPositions().length; ++i) {
                playerPositionList.add(data.getPositions()[i]);
            }
        }

        return playerPositionList;
    }

    @Override
    public void writePosition(PlayerPosition position) throws IOException {
        List<PlayerPosition> playerPositions = listPositions();
        playerPositions.removeIf(p -> StringUtils.equalsIgnoreCase(position.getId(), p.getId()));
        playerPositions.add(position);

        savePositions(playerPositions);
    }

    @Override
    public void deletePosition(String id) throws IOException {
        List<PlayerPosition> playerPositions = listPositions();
        playerPositions.removeIf(p -> StringUtils.equalsIgnoreCase(id, p.getId()));
        savePositions(playerPositions);
    }

    private void dump(ModData data) throws IOException {
        String serialized = gson.toJson(data);
        Files.write(saveFilePath, serialized.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private void savePositions(List<PlayerPosition> playerPositions) throws IOException {
        ModData data = load();
        data.setPositions(playerPositions.toArray(new PlayerPosition[playerPositions.size()]));
        dump(data);
    }

    private ModData load() throws IOException {
        List<String> lines = Files.readAllLines(saveFilePath);
        try {
            ModData data =  gson.fromJson(String.join("", lines), ModData.class);
            
            if(data == null) {
                data = new ModData();
            }
            
            if(data.getDefaultWorldName() == null) {
                data.setDefaultWorldName("");
            }
            
            if(data.getPositions() == null) {
                data.setPositions(new PlayerPosition[] {});
            }
            
            if(data.getConfigData() == null) {
                data.setConfigData(new ConfigData());
            }
            
            return data;
            
        } catch (Exception e) {
            // Fallback for old versions
            ModData data = new ModData();
            data.setDefaultWorldName("");
            data.setPositions(gson.fromJson(String.join("", lines), PlayerPosition[].class));
            data.setConfigData(new ConfigData());

            dump(data);

            return data;
        }
    }

}
