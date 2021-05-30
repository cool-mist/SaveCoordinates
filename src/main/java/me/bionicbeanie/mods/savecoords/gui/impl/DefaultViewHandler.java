package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.UUID;
import java.util.function.Supplier;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.gui.IRootPanel;
import me.bionicbeanie.mods.savecoords.model.PlayerPosition;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import me.bionicbeanie.mods.savecoords.model.PositionMetadata;
import me.bionicbeanie.mods.savecoords.util.ResourceUtils;
import net.minecraft.text.LiteralText;

class DefaultViewHandler extends ViewHandlerBase<PlayerPosition> {

    private IPlayerLocator locator;
    private IFileStore fileStore;
    private WButton saveButton;
    private WButton listButton;
    private WButton pingButton;
    private WButton closeButton;
    private WButton configButton;

    public DefaultViewHandler(IFileStore fileStore, IPlayerLocator locator) {
        this.fileStore = fileStore;
        this.locator = locator;

        this.listButton = CreateButton("LIST");
        this.saveButton = CreateButton("SAVE");
        this.pingButton = CreatePingButton();
        this.closeButton = CreateButton("CLOSE");
        this.configButton = CreateButton("CONF");
    }

    @Override
    public Supplier<PlayerPosition> placeWidgets(IRootPanel root, PlayerPosition existingPosition) {

        PlayerRawPosition rawPosition = existingPosition == null ? locator.locate() : existingPosition;

        WWidget xLabel = CreateLabelForCoorindate("X");
        WWidget yLabel = CreateLabelForCoorindate("Y");
        WWidget zLabel = CreateLabelForCoorindate("Z");

        WWidget xText = CreateWidgetForCoordinate(rawPosition.getX());
        WWidget yText = CreateWidgetForCoordinate(rawPosition.getY());
        WWidget zText = CreateWidgetForCoordinate(rawPosition.getZ());

        root.add(xLabel, 2, 1, 2, 1);
        root.add(yLabel, 2, 2, 2, 1);
        root.add(zLabel, 2, 3, 2, 1);

        root.add(xText, 3, 1, 2, 1);
        root.add(yText, 3, 2, 2, 1);
        root.add(zText, 3, 3, 2, 1);

        WWidget icon = ResourceUtils.CreateWorldIcon(rawPosition.getWorldDimension());
        root.add(icon, 8, 1, 2, 2);

        String defaultWorldName = getDefaultWorldName(existingPosition);
        WTextField world = CreateWorldField(defaultWorldName);
        root.add(world, 0, 4, 4, 1);

        WTextField location = CreateLocationField(existingPosition);
        root.add(location, 5, 4, 7, 1);

        WTextField notes = CreateNotesField(existingPosition);
        root.add(notes, 0, 6, 12, 1);

        root.add(saveButton, 13, 6, 2, 1);
        root.add(listButton, 13, 9, 2, 1);
        root.add(pingButton, 13, 1, 1, 1);
        root.add(closeButton, 0, 9, 2, 1);
        root.add(configButton, 10, 9, 2, 1);

        return createPlayerPositionSupplier(existingPosition, rawPosition, world, location, notes);
    }

    public void onSave(Runnable runnable) {
        this.saveButton.setOnClick(runnable);
    }
    
    public void onClose(Runnable runnable) {
        this.closeButton.setOnClick(runnable);
    }
    
    public void onList(Runnable runnable) {
        this.listButton.setOnClick(runnable);
    }
    
    public void onPing(Runnable runnable) {
        this.pingButton.setOnClick(runnable);
    }
    
    public void onConfig(Runnable runnable) {
        this.configButton.setOnClick(runnable);
    }

    private Supplier<PlayerPosition> createPlayerPositionSupplier(PlayerPosition existingPosition,
            PlayerRawPosition rawPosition, WTextField world, WTextField location, WTextField notes) {

        return () -> {
            String id = CreateId(existingPosition);
            PositionMetadata metadata = CreateMetadata(existingPosition, world, notes);

            return new PlayerPosition(id, rawPosition, location.getText(), metadata);
        };
    }

    private String CreateId(PlayerPosition existingPosition) {
        return existingPosition == null ? UUID.randomUUID().toString() : existingPosition.getId();
    }

    private PositionMetadata CreateMetadata(PlayerPosition existingPosition, WTextField world, WTextField notes) {
        if (existingPosition == null) {
            return new PositionMetadata(world.getText(), notes.getText());
        }

        existingPosition.getPositionMetadata().setWorldName(world.getText());
        existingPosition.getPositionMetadata().setNotes(notes.getText());
        existingPosition.getPositionMetadata().updateLastModified();

        return existingPosition.getPositionMetadata();
    }

    private String getDefaultWorldName(PlayerPosition existingPosition) {
        if (existingPosition != null && existingPosition.getPositionMetadata().getWorldName() != null) {
            return existingPosition.getPositionMetadata().getWorldName();
        }

        try {
            return fileStore.getDefaultWorld();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private WWidget CreateLabelForCoorindate(String label) {
        return new WLabel(label, 0xb80000);
    }

    private WWidget CreateWidgetForCoordinate(long l) {
        return new WText(new LiteralText(String.valueOf(l)), 0x3939ac);
    }

    private WTextField CreateLocationField(PlayerPosition existingPosition) {
        WTextField location = new WTextField(new LiteralText("location name"));

        if (existingPosition != null) {
            location.setText(existingPosition.getLocationName());
        }

        location.setMaxLength(20);

        return location;
    }

    private WTextField CreateNotesField(PlayerPosition existingPosition) {
        WTextField notes = new WTextField(new LiteralText("additional notes"));

        if (existingPosition != null && existingPosition.getPositionMetadata() != null) {
            notes.setText(existingPosition.getPositionMetadata().getNotes());
        }

        return notes;
    }

    private WTextField CreateWorldField(String defaultWorld) {
        WTextField world = new WTextField(new LiteralText("world name"));
        world.setMaxLength(7);
        world.setText(defaultWorld);
        return world;
    }

    private WButton CreatePingButton() {
        WButton button = CreateButton("");
        button.setIcon(ResourceUtils.CreatePingIcon());
        return button;
    }

    private WButton CreateButton(String name) {
        WButton button = new WButton(new LiteralText(name));

        return button;
    }
}
