package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.UUID;
import java.util.function.Supplier;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.IPlayerLocator;
import me.bionicbeanie.mods.savecoords.TranslationKeys;
import me.bionicbeanie.mods.savecoords.gui.IRootPanel;
import me.bionicbeanie.mods.savecoords.model.PlayerPosition;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import me.bionicbeanie.mods.savecoords.model.PositionMetadata;
import me.bionicbeanie.mods.savecoords.util.DimensionKeys;
import me.bionicbeanie.mods.savecoords.util.ResourceUtils;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

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

        this.listButton = CreateButton(TranslationKeys.MENU_LIST);
        this.saveButton = CreateButton(TranslationKeys.MENU_SAVE);
        this.pingButton = CreatePingButton();
        this.closeButton = CreateButton(TranslationKeys.MENU_CLOSE);
        this.configButton = CreateButton(TranslationKeys.MENU_CONF);
    }

    @Override
    public Supplier<PlayerPosition> setupView(IRootPanel root, PlayerPosition existingPosition) {

        PlayerRawPosition rawPosition = existingPosition == null ? locator.locate() : existingPosition;

        WWidget xLabel = CreateLabelForCoordinate("X");
        WWidget yLabel = CreateLabelForCoordinate("Y");
        WWidget zLabel = CreateLabelForCoordinate("Z");

        WTextField xText = CreateWidgetForCoordinate(rawPosition.getX());
        WTextField yText = CreateWidgetForCoordinate(rawPosition.getY());
        WTextField zText = CreateWidgetForCoordinate(rawPosition.getZ());
        
        root.add(xLabel, 0, 1, 1, 1);
        root.add(yLabel, 0, 2, 1, 1);
        root.add(zLabel, 0, 3, 1, 1);

        root.add(xText, 1, 1, 6, 1);
        root.add(yText, 1, 2, 6, 1);
        root.add(zText, 1, 3, 6, 1);
        
        WSprite worldIcon = ResourceUtils.createWorldIcon(getDimension(rawPosition));
        root.add(worldIcon, 9, 1, 2, 2);
        
        WWidget worldButton = CreateWorldButton(worldIcon);
        root.add(worldButton, 8, 3, 4, 1);

        String defaultWorldName = getDefaultWorldName(existingPosition);
        WTextField world = CreateWorldField(defaultWorldName);
        root.add(world, 0, 5, 4, 1);

        WTextField location = CreateLocationField(existingPosition);
        root.add(location, 5, 5, 7, 1);

        WTextField notes = CreateNotesField(existingPosition);
        root.add(notes, 0, 7, 12, 1);

        root.add(saveButton, 13, 7, 2, 1);
        root.add(listButton, 13, 9, 2, 1);
        root.add(pingButton, 13, 1, 1, 1);
        root.add(closeButton, 0, 9, 2, 1);
        root.add(configButton, 10, 9, 2, 1);

        return createPlayerPositionSupplier(existingPosition, xText, yText, zText, world, location, notes);
    }

    private WWidget CreateWorldButton(WSprite worldIcon) {
        WButton button = new WButton(new LiteralText(DimensionKeys.OVERWORLD));
        button.setOnClick(() -> button.setLabel(new LiteralText(DimensionKeys.NETHER)));
        return button;
    }

    private String getDimension(PlayerRawPosition rawPosition) {
        String dimensionIdentifier = rawPosition.getWorldDimension();
        
        if(isDimension(DimensionKeys.OVERWORLD, dimensionIdentifier)) {
            return DimensionKeys.OVERWORLD;
        }
        
        if(isDimension(DimensionKeys.NETHER, dimensionIdentifier)) {
            return DimensionKeys.NETHER;
        }
        
        if(isDimension(DimensionKeys.END, dimensionIdentifier)) {
            return DimensionKeys.END;
        }
        
        return DimensionKeys.UNKNOWN;
    }

    private boolean isDimension(String dimension, String dimensionIdentifier) {
        return dimensionIdentifier != null && dimensionIdentifier.contains(dimension);
    }

    public void onSaveButtonClick(Runnable runnable) {
        this.saveButton.setOnClick(runnable);
    }

    public void onCloseButtonClick(Runnable runnable) {
        this.closeButton.setOnClick(runnable);
    }

    public void onListButtonClick(Runnable runnable) {
        this.listButton.setOnClick(runnable);
    }

    public void onPingButtonClick(Runnable runnable) {
        this.pingButton.setOnClick(runnable);
    }

    public void onConfigButtonClick(Runnable runnable) {
        this.configButton.setOnClick(runnable);
    }

    private Supplier<PlayerPosition> createPlayerPositionSupplier(PlayerPosition existingPosition,
            WTextField xText, WTextField yText, WTextField zText, WTextField world, 
            WTextField location, WTextField notes) {

        return () -> {
            String id = CreateId(existingPosition);
            long x = parseLong(xText);
            long y = parseLong(yText);
            long z = parseLong(zText);
            
            PlayerRawPosition rawPosition = new PlayerRawPosition(x, y, z, "overworld"); 
            PositionMetadata metadata = CreateMetadata(existingPosition, world, notes);

            return new PlayerPosition(id, rawPosition, location.getText(), metadata);
        };
    }

    private long parseLong(WTextField xText) {
        String value = xText.getText();
        return Long.parseLong(value);
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
            return fileStore.readDefaultWorldName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private WWidget CreateLabelForCoordinate(String label) {
        //return new WLabel(label, 0xb80000);
        WButton labelButton = new WButton(new LiteralText(label));
        labelButton.setEnabled(false);
        return labelButton;
    }

    private WTextField CreateWidgetForCoordinate(long l) {
        WTextField textField = new WTextField();

        textField.setText(String.valueOf(l));

        return textField;
    }

    private WTextField CreateLocationField(PlayerPosition existingPosition) {
        WTextField location = new WTextField(new TranslatableText(TranslationKeys.MENU_LOCATION));

        if (existingPosition != null) {
            location.setText(existingPosition.getLocationName());
        }

        location.setMaxLength(20);

        return location;
    }

    private WTextField CreateNotesField(PlayerPosition existingPosition) {
        WTextField notes = new WTextField(new TranslatableText(TranslationKeys.MENU_NOTES));

        if (existingPosition != null && existingPosition.getPositionMetadata() != null) {
            notes.setText(existingPosition.getPositionMetadata().getNotes());
        }

        return notes;
    }

    private WTextField CreateWorldField(String defaultWorld) {
        WTextField world = new WTextField(new TranslatableText(TranslationKeys.MENU_WORLD_NAME));
        world.setMaxLength(7);
        world.setText(defaultWorld);
        return world;
    }

    private WButton CreatePingButton() {
        WButton button = CreateButton("");
        button.setIcon(ResourceUtils.createPingIcon());
        return button;
    }

    private WButton CreateButton(String translationKey) {
        return new WButton(new TranslatableText(translationKey));
    }
}
