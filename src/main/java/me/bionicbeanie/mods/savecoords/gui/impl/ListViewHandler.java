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
import me.bionicbeanie.mods.savecoords.INetherCalculator;
import me.bionicbeanie.mods.savecoords.TranslationKeys;
import me.bionicbeanie.mods.savecoords.gui.IRootPanel;
import me.bionicbeanie.mods.savecoords.model.PlayerPosition;
import me.bionicbeanie.mods.savecoords.model.PlayerRawPosition;
import me.bionicbeanie.mods.savecoords.util.ResourceUtils;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

class ListViewHandler extends ViewHandlerBase<Void> {

    private IFileStore fileStore;
    private WButton backButton;
    private Consumer<PlayerPosition> onDelete;
    private Consumer<PlayerPosition> onEdit;
    private Consumer<PlayerRawPosition> onPing;
    private INetherCalculator netherCalculator;

    public ListViewHandler(IFileStore fileStore, Consumer<PlayerPosition> onDelete, Consumer<PlayerPosition> onEdit,
            Consumer<PlayerRawPosition> onPing, INetherCalculator netherCalculator) {
        this.fileStore = fileStore;
        this.backButton = createBackButton();
        this.onDelete = onDelete;
        this.onEdit = onEdit;
        this.onPing = onPing;
        this.netherCalculator = netherCalculator;
    }

    @Override
    public Supplier<Void> setupView(IRootPanel root, Void nullState) {

        List<PlayerPosition> positions = getPositions(fileStore);
        WListPanel<PlayerPosition, CoordinatesListItemPanel> listPanel = createListPanel(positions);

        root.add(listPanel, 0, 0, 15, 9);
        root.add(backButton, 0, 9, 2, 1);

        return () -> (Void) null;
    }

    public void onBackButtonClick(Runnable runnable) {
        this.backButton.setOnClick(runnable);
    }

    private WButton createBackButton() {
        return new WButton(new TranslatableText(TranslationKeys.MENU_BACK));
    }

    private WListPanel<PlayerPosition, CoordinatesListItemPanel> createListPanel(List<PlayerPosition> positions) {
        BiConsumer<PlayerPosition, CoordinatesListItemPanel> configurator = (pos, p) -> p.setPosition(pos);
        WListPanel<PlayerPosition, CoordinatesListItemPanel> panel = createListPanel(positions, configurator);

        panel.setListItemHeight(2 * 18);

        return panel;
    }

    private WListPanel<PlayerPosition, CoordinatesListItemPanel> createListPanel(List<PlayerPosition> positions,
            BiConsumer<PlayerPosition, CoordinatesListItemPanel> config) {
        return new WListPanel<>(positions, this::createListPanel, config);
    }

    private CoordinatesListItemPanel createListPanel() {
        return new CoordinatesListItemPanel(onDelete, onEdit, onPing, netherCalculator);
    }

    private List<PlayerPosition> getPositions(IFileStore fileStore) {
        try {
            List<PlayerPosition> positions = fileStore.listPositions();
            Collections.sort(positions, (p1, p2) -> {
                if (p1.getPositionMetadata() != null && p2.getPositionMetadata() != null) {
                    return p2.getPositionMetadata().getLastModifiedMillis() > p1.getPositionMetadata()
                            .getLastModifiedMillis() ? 1 : -1;
                }

                return -1;
            });
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
        private WButton detailButton;
        private WButton pingButton;
        private WButton convertButton;
        private WLabel world;
        private Consumer<PlayerRawPosition> onPing;
        private Consumer<PlayerPosition> onEdit;
        private Consumer<PlayerPosition> onDelete;
        private INetherCalculator netherCalculator;
        private boolean coordinateConvertState;

        CoordinatesListItemPanel(Consumer<PlayerPosition> onDelete, Consumer<PlayerPosition> onEdit,
                Consumer<PlayerRawPosition> onPing, INetherCalculator netherCalculator) {

            this.onDelete = onDelete;
            this.onEdit = onEdit;
            this.onPing = onPing;
            this.netherCalculator = netherCalculator;
            this.coordinateConvertState = true;

            this.coordinates = new WLabel("");
            this.location = new WLabel("");
            this.world = new WLabel("");
            this.icon = new WSprite(ResourceUtils.getIdentifier("close"));
            this.deleteButton = createDeleteButton();
            this.detailButton = new WButton(new LiteralText(""));
            this.pingButton = new WButton(new LiteralText(""));
            this.convertButton = new WButton(new LiteralText(""));

            this.pingButton.setIcon(ResourceUtils.createPingIcon());
            this.detailButton.setIcon(ResourceUtils.createDetailsIcon());
            this.convertButton.setIcon(ResourceUtils.createConvertIcon(this.coordinateConvertState));

            this.add(icon, 0, 0, 1 * 9, 1 * 9);
            this.add(world, 1 * 18, 0, 3 * 18, 1 * 18);
            this.add(location, 4 * 18, 0, 4 * 18, 1 * 18);
            this.add(coordinates, 3 * 18, 1 * 18, 9 * 18, 1 * 18);
            this.add(deleteButton, 13 * 18, 0, 1 * 18, 1 * 18);
            this.add(detailButton, 12 * 18 - 1, 0, 1 * 18, 1 * 18);
            this.add(convertButton, 11 * 18 - 2, 0, 1 * 18, 1 * 18);
            this.add(pingButton, 10 * 18 - 3, 0, 1 * 18, 1 * 18);

            this.icon.setSize(1 * 15, 1 * 15);
            this.world.setSize(3 * 18, 1 * 18);
            this.location.setSize(9 * 18, 1 * 18);
            this.coordinates.setSize(2 * 18, 9 * 18);
            this.deleteButton.setSize(1 * 18, 1 * 18);
            this.pingButton.setSize(1 * 18, 1 * 18);
            this.detailButton.setSize(1 * 18, 1 * 18);
            this.convertButton.setSize(1 * 18, 1 * 18);

            this.setSize(15 * 18, 2 * 18);
        }

        private WButton createDeleteButton() {
            TexturedButton button = new TexturedButton(new LiteralText("x"));
            button.setTexture(ResourceUtils.getIdentifier("close"));

            return button;
        }

        void setPosition(PlayerPosition position) {
            this.location.setText(new LiteralText(position.getLocationName()));
            this.location.setColor(0x3939ac);
            if (position.getPositionMetadata() != null) {
                this.world.setText(new LiteralText("[" + position.getPositionMetadata().getWorldName() + "]"));
                this.world.setColor(0xb80000);
            }
            
            setRawPosition(position);
            
            this.deleteButton.setOnClick(() -> onDelete.accept(position));
            this.pingButton.setOnClick(() -> onPing.accept(position));
            this.detailButton.setOnClick(() -> onEdit.accept(position));
            this.convertButton.setOnClick(() -> setRawPosition(netherCalculator.convert(position)));
        }

        private void setRawPosition(PlayerPosition position) {
            
            this.icon.setImage(ResourceUtils.getIdentifier(position.getWorldDimension()));
            this.coordinates
                    .setText(new LiteralText(position.getX() + ", " + position.getY() + ", " + position.getZ()));
            this.convertButton.setOnClick(() -> setRawPosition(netherCalculator.convert(position)));
            this.pingButton.setOnClick(() -> onPing.accept(position));
            this.coordinateConvertState = !coordinateConvertState;
            this.convertButton.setIcon(ResourceUtils.createConvertIcon(coordinateConvertState));
        }
    }
}
