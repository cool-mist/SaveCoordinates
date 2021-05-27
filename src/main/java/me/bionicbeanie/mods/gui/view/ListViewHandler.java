package me.bionicbeanie.mods.gui.view;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;
import me.bionicbeanie.mods.api.IRootGridPanel;
import me.bionicbeanie.mods.model.PlayerPosition;
import me.bionicbeanie.mods.util.DimensionSpriteUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class ListViewHandler extends ViewHandlerBase {

    public ListViewHandler(IFileStore fileStore, IGui gui, MinecraftClient client) {
        super(fileStore, gui, client);
    }

    @Override
    public void placeWidgets(IRootGridPanel root) {

        BiConsumer<PlayerPosition, CoordinatesListItemPanel> configurator = (PlayerPosition position,
                CoordinatesListItemPanel panel) -> {
            panel.setPosition(position, fileStore, gui);
        };

        List<PlayerPosition> positions;
        try {
            positions = fileStore.list();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        WListPanel<PlayerPosition, CoordinatesListItemPanel> listPanel = new WListPanel<>(positions,
                CoordinatesListItemPanel::new, configurator);

        listPanel.setListItemHeight(2 * 18);

        root.add(listPanel, 0, 0, 11, 11);
    }

    public static class CoordinatesListItemPanel extends WPlainPanel {

        private WLabel coordinates;
        private WLabel location;
        private WSprite icon;
        private WButton deleteButton;

        public CoordinatesListItemPanel() {
            this.coordinates = new WLabel("Foo");
            this.location = new WLabel("Foo");
            this.icon = new WSprite(new Identifier("minecraft:textures/item/ender_eye.png"));
            this.deleteButton = new WButton(new LiteralText("x"));

            this.add(icon, 0, 0, 1 * 9, 1 * 9);
            this.add(location, 1 * 18, 0, 4 * 18, 1 * 18);
            this.add(coordinates, 1 * 18, 1 * 18, 9 * 18, 1 * 18);
            this.add(deleteButton, 9 * 18, 0, 1 * 18, 1 * 18);

            this.icon.setSize(1 * 15, 1 * 15);
            this.location.setSize(9 * 18, 1 * 18);
            this.coordinates.setSize(2 * 18, 9 * 18);
            this.deleteButton.setSize(1 * 18, 1 * 18);
            this.setSize(9 * 18, 2 * 18);
        }

        public void setPosition(PlayerPosition position, IFileStore fileStore, IGui gui) {
            this.icon.setImage(DimensionSpriteUtil.CreateWorldIconIdentifier(position.getWorldDimension()));
            this.location.setText(new LiteralText(position.getLocationName()));
            this.location.setColor(0x3939ac);
            this.coordinates
                    .setText(new LiteralText(position.getX() + ", " + position.getY() + ", " + position.getZ()));
            this.deleteButton.setOnClick(new Runnable() {

                @Override
                public void run() {
                    try {
                        fileStore.delete(position.getId());
                        gui.showListView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
