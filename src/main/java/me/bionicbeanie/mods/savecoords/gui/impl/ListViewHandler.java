package me.bionicbeanie.mods.savecoords.gui.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import me.bionicbeanie.mods.savecoords.IFileStore;
import me.bionicbeanie.mods.savecoords.gui.IRootPanel;
import me.bionicbeanie.mods.savecoords.model.PlayerPosition;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import me.bionicbeanie.mods.savecoords.util.ResourceUtils;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

class ListViewHandler extends ViewHandlerBase<Void> {

    private IFileStore fileStore;
    private WButton backButton;
    private Consumer<PlayerPosition> onDelete;
    private Consumer<PlayerPosition> onEdit;
    private Consumer<PlayerRawPosition> onPing;

    public ListViewHandler(IFileStore fileStore, Consumer<PlayerPosition> onDelete, Consumer<PlayerPosition> onEdit,
            Consumer<PlayerRawPosition> onPing) {
        this.fileStore = fileStore;
        this.backButton = createBackButton();
        this.onDelete = onDelete;
        this.onEdit = onEdit;
        this.onPing = onPing;
    }

    @Override
    public Supplier<Void> placeWidgets(IRootPanel root, Void nullState) {

        List<PlayerPosition> positions = getPositions(fileStore);
        WListPanel<PlayerPosition, CoordinatesListItemPanel> listPanel = createListPane(positions);

        root.add(listPanel, 0, 0, 15, 9);
        root.add(backButton, 0, 9, 2, 1);

        return () -> (Void) null;
    }

    public void onBack(Runnable runnable) {
        this.backButton.setOnClick(runnable);
    }

    private WButton createBackButton() {
        return new WButton(new LiteralText("BACK"));
    }

    private WListPanel<PlayerPosition, CoordinatesListItemPanel> createListPane(List<PlayerPosition> positions) {
        BiConsumer<PlayerPosition, CoordinatesListItemPanel> configurator = (pos, p) -> p.setPosition(pos, fileStore);
        WListPanel<PlayerPosition, CoordinatesListItemPanel> panel = CreateListPanel(positions, configurator);

        panel.setListItemHeight(2 * 18);

        return panel;
    }

    private WListPanel<PlayerPosition, CoordinatesListItemPanel> CreateListPanel(List<PlayerPosition> positions,
            BiConsumer<PlayerPosition, CoordinatesListItemPanel> config) {
        return new WListPanel<>(positions, this::createListPanel, config);
    }

    private CoordinatesListItemPanel createListPanel() {
        return new CoordinatesListItemPanel(onDelete, onEdit, onPing);
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

    private static class CoordinatesListItemPanel extends WPlainPanel {

        private WLabel coordinates;
        private WLabel location;
        private WSprite icon;
        private WButton deleteButton;
        private WButton pingButton;
        private WButton detailButton;
        private WLabel world;
        private Consumer<PlayerRawPosition> onPing;
        private Consumer<PlayerPosition> onEdit;
        private Consumer<PlayerPosition> onDelete;

        CoordinatesListItemPanel(Consumer<PlayerPosition> onDelete, Consumer<PlayerPosition> onEdit,
                Consumer<PlayerRawPosition> onPing) {

            this.onDelete = onDelete;
            this.onEdit = onEdit;
            this.onPing = onPing;

            this.coordinates = new WLabel("Foo");
            this.location = new WLabel("Foo");
            this.world = new WLabel("Foo");
            this.icon = new WSprite(new Identifier("minecraft:textures/item/ender_eye.png"));
            this.deleteButton = new WButton(new LiteralText("x"));
            this.pingButton = new WButton(new LiteralText(""));
            this.detailButton = new WButton(new LiteralText(""));

            this.deleteButton.setIcon(ResourceUtils.CreateCloseIcon());
            this.pingButton.setIcon(ResourceUtils.CreatePingIcon());
            this.detailButton.setIcon(ResourceUtils.CreateDetailsIcon());

            this.add(icon, 0, 0, 1 * 9, 1 * 9);
            this.add(world, 1 * 18, 0, 3 * 18, 1 * 18);
            this.add(location, 4 * 18, 0, 4 * 18, 1 * 18);
            this.add(coordinates, 3 * 18, 1 * 18, 9 * 18, 1 * 18);
            this.add(deleteButton, 13 * 18, 0, 1 * 18, 1 * 18);
            this.add(pingButton, 1 * 18, 1 * 18, 1 * 18, 1 * 18);
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

        void setPosition(PlayerPosition position, IFileStore fileStore) {
            this.icon.setImage(ResourceUtils.CreateWorldIconIdentifier(position.getWorldDimension()));
            this.location.setText(new LiteralText(position.getLocationName()));
            this.location.setColor(0x3939ac);
            this.world.setText(new LiteralText("[" + position.getPositionMetadata().getWorldName() + "]"));
            this.world.setColor(0xb80000);
            this.coordinates
                    .setText(new LiteralText(position.getX() + ", " + position.getY() + ", " + position.getZ()));
            this.deleteButton.setOnClick(() -> onDelete.accept(position));
            this.pingButton.setOnClick(() -> onPing.accept(position));
            this.detailButton.setOnClick(() -> onEdit.accept(position));
        }
    }
}
