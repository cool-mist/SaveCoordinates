package me.bionicbeanie.mods.gui.view;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.api.IPlayerLocator;
import me.bionicbeanie.mods.api.IRootGridPanel;
import me.bionicbeanie.mods.model.PlayerPosition;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import me.bionicbeanie.mods.util.DimensionSpriteUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class DefaultViewHandler extends ViewHandlerBase<PlayerPosition> {

    private IPlayerLocator locator;

    public DefaultViewHandler(IFileStore fileStore, IPlayerLocator locator, MinecraftClient client, IGui gui) {
        super(fileStore, gui, client);
        this.locator = locator;
    }

    @Override
    public void placeWidgets(IRootGridPanel root, PlayerPosition existingPosition) {

        PlayerRawPosition rawPosition = existingPosition == null ? locator.locate(client) : existingPosition;

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

        WWidget icon = DimensionSpriteUtil.CreateWorldIcon(rawPosition.getWorldDimension());
        root.add(icon, 8, 1, 2, 2);

        String defaultWorld = getDefaultWorld(existingPosition);
        WTextField world = CreateWorldField(defaultWorld);
        root.add(world, 0, 4, 4, 1);

        WTextField location = CreateLocationField(existingPosition);
        root.add(location, 5, 4, 7, 1);
        
        WTextField notes = CreateNotesField(existingPosition);
        root.add(notes, 0, 6, 12, 1);

        WWidget save = CreateSaveButton(rawPosition, location, notes, world, existingPosition);
        root.add(save, 13, 6, 2, 1);

        WWidget list = CreateListButton();
        root.add(list, 13, 9, 2, 1);

        WWidget ping = CreatePingButton(rawPosition);
        root.add(ping, 13, 1, 2, 1);

        WWidget close = CreateCloseButton();
        root.add(close, 0, 9, 2, 1);
    }

    private String getDefaultWorld(PlayerPosition existingPosition) {
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

    private WWidget CreateSaveButton(PlayerRawPosition rawPosition, WTextField location, WTextField notes,
            WTextField world, PlayerPosition existingPosition) {
        WButton button = new WButton(new LiteralText("SAVE"));
        button.setOnClick(new SaveOperation(fileStore, gui, rawPosition, world, location, notes, existingPosition));
        return button;
    }

    private WWidget CreateListButton() {
        WButton button = new WButton(new LiteralText("LIST"));
        button.setOnClick(gui::showListView);
        return button;
    }

    private WWidget CreatePingButton(PlayerRawPosition rawPosition) {
        WButton button = new WButton(new LiteralText("PING"));
        button.setOnClick(new PingOperation(fileStore, gui, rawPosition));
        return button;
    }

    private WWidget CreateCloseButton() {
        WButton button = new WButton(new LiteralText("CLOSE"));
        button.setOnClick(gui::close);
        return button;
    }
}
