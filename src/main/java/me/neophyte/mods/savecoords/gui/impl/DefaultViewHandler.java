package me.neophyte.mods.savecoords.gui.impl;

import java.util.UUID;
import java.util.function.Supplier;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.neophyte.mods.savecoords.IDimensionAware;
import me.neophyte.mods.savecoords.IFileStore;
import me.neophyte.mods.savecoords.IPlayerLocator;
import me.neophyte.mods.savecoords.TranslationKeys;
import me.neophyte.mods.savecoords.gui.IRootPanel;
import me.neophyte.mods.savecoords.model.PlayerPosition;
import me.neophyte.mods.savecoords.model.PlayerRawPosition;
import me.neophyte.mods.savecoords.model.PositionMetadata;
import me.neophyte.mods.savecoords.util.ResourceUtils;
import net.minecraft.text.Text;

class DefaultViewHandler extends ViewHandlerBase<PlayerPosition> {

    private IPlayerLocator locator;
    private IFileStore fileStore;
    private WButton saveButton;
    private WButton listButton;
    private WButton pingButton;
    private WButton closeButton;
    private WButton configButton;
    private IDimensionAware dimensionAware;

    public DefaultViewHandler(IFileStore fileStore, IPlayerLocator locator, IDimensionAware dimensionAware) {
        this.fileStore = fileStore;
        this.locator = locator;
        this.dimensionAware = dimensionAware;

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

        DimensionSprite dimensionSprite = new DimensionSprite();
        root.add(dimensionSprite, 9, 0, 2, 2);

        DimensionButton dimensionButton = CreateDimensionButton(dimensionSprite, existingPosition);
        root.add(dimensionButton, 8, 3, 4, 1);

        String defaultWorldName = getDefaultWorldName(existingPosition);
        WTextField worldNameField = CreateWorldField(defaultWorldName);
        root.add(worldNameField, 0, 5, 4, 1);

        WTextField locationField = CreateLocationField(existingPosition);
        root.add(locationField, 5, 5, 7, 1);

        WTextField notesField = CreateNotesField(existingPosition);
        root.add(notesField, 0, 7, 12, 1);

        root.add(saveButton, 13, 7, 2, 1);
        root.add(listButton, 13, 9, 2, 1);
        root.add(pingButton, 13, 1, 1, 1);
        root.add(closeButton, 0, 9, 2, 1);
        root.add(configButton, 10, 9, 2, 1);

        return createPlayerPositionSupplier(
                existingPosition, 
                xText, 
                yText, 
                zText, 
                dimensionButton, 
                worldNameField, 
                locationField,
                notesField);
    }

    private DimensionButton CreateDimensionButton(WSprite worldIcon, PlayerRawPosition existingPosition) {
        return new DimensionButton(worldIcon, dimensionAware, existingPosition);
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

    private Supplier<PlayerPosition> createPlayerPositionSupplier(PlayerPosition existingPosition, WTextField xText,
            WTextField yText, WTextField zText, DimensionButton dimensionButton, WTextField world, WTextField location,
            WTextField notes) {

        return () -> {
            String id = CreateId(existingPosition);
            long x = parseLong(xText);
            long y = parseLong(yText);
            long z = parseLong(zText);

            PlayerRawPosition rawPosition = new PlayerRawPosition(x, y, z, dimensionButton.getDimension().getName());
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
        WButton labelButton = new WButton(Text.of(label));
        labelButton.setEnabled(false);
        return labelButton;
    }

    private WTextField CreateWidgetForCoordinate(long l) {
        WTextField textField = new WTextField();

        textField.setText(String.valueOf(l));

        return textField;
    }

    private WTextField CreateLocationField(PlayerPosition existingPosition) {
        WTextField location = new WTextField(Text.translatable(TranslationKeys.MENU_LOCATION));

        if (existingPosition != null) {
            location.setText(existingPosition.getLocationName());
        }

        location.setMaxLength(20);

        return location;
    }

    private WTextField CreateNotesField(PlayerPosition existingPosition) {
        WTextField notes = new WTextField(Text.translatable(TranslationKeys.MENU_NOTES));

        if (existingPosition != null && existingPosition.getPositionMetadata() != null) {
            notes.setText(existingPosition.getPositionMetadata().getNotes());
        }

        return notes;
    }

    private WTextField CreateWorldField(String defaultWorld) {
        WTextField world = new WTextField(Text.translatable(TranslationKeys.MENU_WORLD_NAME));
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
        return new WButton(Text.translatable(translationKey));
    }
}
