package me.bionicbeanie.mods.gui.view;

import java.io.IOException;
import java.util.Collections;
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

public class ListViewHandler extends ViewHandlerBase<Void> {

    public ListViewHandler(IFileStore fileStore, IGui gui, MinecraftClient client) {
        super(fileStore, gui, client);
    }

    @Override
    public void placeWidgets(IRootGridPanel root, Void nullState) {

        List<PlayerPosition> positions = getPositions(fileStore);
        WListPanel<PlayerPosition, CoordinatesListItemPanel> listPanel = createListPane(positions);

        WButton backButton = createBackButton();

        root.add(listPanel, 0, 0, 15, 9);
        root.add(backButton, 0, 9, 2, 1);
    }

    private WButton createBackButton() {
        WButton backButton = new WButton(new LiteralText("BACK"));
        backButton.setOnClick(gui::showDefaultView);
        return backButton;
    }

    private WListPanel<PlayerPosition, CoordinatesListItemPanel> createListPane(List<PlayerPosition> positions) {
        BiConsumer<PlayerPosition, CoordinatesListItemPanel> configurator = (position, panel) -> {
            panel.setPosition(position, fileStore, gui);
        };

        WListPanel<PlayerPosition, CoordinatesListItemPanel> panel = new WListPanel<>(positions,
                CoordinatesListItemPanel::new, configurator);

        panel.setListItemHeight(2 * 18);

        return panel;
    }

    private List<PlayerPosition> getPositions(IFileStore fileStore) {
        try {
            List<PlayerPosition> positions = fileStore.list();
            Collections.sort(positions, (p1, p2) -> p2.getPositionMetadata().getLastModified()
                    .compareTo(p1.getPositionMetadata().getLastModified()));
            return positions;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static class CoordinatesListItemPanel extends WPlainPanel {

        private WLabel coordinates;
        private WLabel location;
        private WSprite icon;
        private WButton deleteButton;
        private WButton pingButton;
        private WButton detailButton;
        private WLabel world;

        public CoordinatesListItemPanel() {
            this.coordinates = new WLabel("Foo");
            this.location = new WLabel("Foo");
            this.world = new WLabel("Foo");
            this.icon = new WSprite(new Identifier("minecraft:textures/item/ender_eye.png"));
            this.deleteButton = new WButton(new LiteralText("x"));
            this.pingButton = new WButton(new LiteralText("!"));
            this.detailButton = new WButton(new LiteralText("+"));

            this.add(icon, 0, 0, 1 * 9, 1 * 9);
            this.add(world, 1 * 18, 0, 3 * 18, 1 * 18);
            this.add(location, 4 * 18, 0, 4 * 18, 1 * 18);
            this.add(coordinates, 1 * 18, 1 * 18, 9 * 18, 1 * 18);
            this.add(deleteButton, 13 * 18, 0, 1 * 18, 1 * 18);
            this.add(pingButton, 12 * 18, 0, 1 * 18, 1 * 18);
            this.add(detailButton, 11 * 18, 0, 1 * 18, 1 * 18);

            this.icon.setSize(1 * 15, 1 * 15);
            this.world.setSize(3 * 18, 1 * 18);
            this.location.setSize(9 * 18, 1 * 18);
            this.coordinates.setSize(2 * 18, 9 * 18);
            this.deleteButton.setSize(1 * 18, 1 * 18);
            this.pingButton.setSize(1 * 18, 1 * 18);
            this.detailButton.setSize(1 * 18, 1 * 18);

            this.setSize(15 * 18, 2 * 18);
        }

        public void setPosition(PlayerPosition position, IFileStore fileStore, IGui gui) {
            this.icon.setImage(DimensionSpriteUtil.CreateWorldIconIdentifier(position.getWorldDimension()));
            this.location.setText(new LiteralText(position.getLocationName()));
            this.location.setColor(0x3939ac);
            this.world.setText(new LiteralText("[" + position.getPositionMetadata().getWorldName() + "]"));
            this.world.setColor(0xb80000);
            this.coordinates
                    .setText(new LiteralText(position.getX() + ", " + position.getY() + ", " + position.getZ()));
            this.deleteButton.setOnClick(new DeleteOperation(fileStore, gui, position));
            this.pingButton.setOnClick(new PingOperation(fileStore, gui, position));
            this.detailButton.setOnClick(new ShowDetailOperation(fileStore, gui, position));
        }
    }
}
