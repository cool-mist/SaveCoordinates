package me.bionicbeanie.mods.gui.view;

import java.util.UUID;

import io.github.cottonmc.cotton.gui.widget.WTextField;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.model.PlayerPosition;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import me.bionicbeanie.mods.model.PositionMetadata;

public class SaveOperation extends ViewOperationBase {

    private PlayerRawPosition rawPosition;
    private WTextField location, notes;
    private WTextField world;
    private PlayerPosition existingPosition;

    public SaveOperation(IFileStore fileStore, IGui gui, PlayerRawPosition rawPosition, WTextField world,
            WTextField location, WTextField notes, PlayerPosition existingPosition) {
        super(fileStore, gui);
        this.rawPosition = rawPosition;
        this.existingPosition = existingPosition;
        this.world = world;
        this.location = location;
        this.notes = notes;
    }

    @Override
    protected void executeOperation(IFileStore fileStore, IGui gui) throws Exception {

        String id = existingPosition == null ? UUID.randomUUID().toString() : existingPosition.getId();
        PositionMetadata metadata = CreateMetadata(existingPosition);
        
        PlayerPosition position = new PlayerPosition(id, rawPosition, this.location.getText(), metadata);

        fileStore.save(position);
        fileStore.setDefaultWorld(position.getPositionMetadata().getWorldName());
        gui.showListView();
        gui.setDefaultViewState(null);
    }

    private PositionMetadata CreateMetadata(PlayerPosition existingPosition) {
        if(existingPosition == null) {
            return new PositionMetadata(this.world.getText(), this.notes.getText());
        }
        
        existingPosition.getPositionMetadata().setWorldName(this.world.getText());
        existingPosition.getPositionMetadata().setNotes(this.notes.getText());
        existingPosition.getPositionMetadata().updateLastModified();
        
        return existingPosition.getPositionMetadata();
    }
}
