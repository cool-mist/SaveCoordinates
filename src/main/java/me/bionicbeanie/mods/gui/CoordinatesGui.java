package me.bionicbeanie.mods.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.api.ICoordinateListHandler;
import me.bionicbeanie.mods.api.ICoordinateSaveHandler;
import me.bionicbeanie.mods.api.IScreen;
import me.bionicbeanie.mods.model.PlayerPosition;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import me.bionicbeanie.mods.util.DimensionSpriteUtil;
import net.minecraft.text.LiteralText;

public class CoordinatesGui extends LightweightGuiDescription {

    private WGridPanel root;
    private ICoordinateSaveHandler coordinateSaveHandler;
    private ICoordinateListHandler coordinateListHandler;
    private PlayerRawPosition rawPosition;
    private List<WWidget> children;
    private IScreen screen;

    public CoordinatesGui(PlayerRawPosition rawPosition, ICoordinateSaveHandler onSave, IScreen screen,
            ICoordinateListHandler onList) {
        this.root = createRootPanel();
        this.rawPosition = rawPosition;
        this.coordinateSaveHandler = onSave;
        this.coordinateListHandler = onList;
        this.screen = screen;
        this.children = new ArrayList<>();

        reset();
        initializeDefaultView();

        root.validate(this);
    }

    private void initializeDefaultView() {
        WWidget xText = CreateWidgetForCoordinate(rawPosition.getX());
        WWidget yText = CreateWidgetForCoordinate(rawPosition.getY());
        WWidget zText = CreateWidgetForCoordinate(rawPosition.getZ());

        add(xText, 0, 1, 2, 1);
        add(yText, 0, 2, 2, 1);
        add(zText, 0, 3, 2, 1);

        WWidget icon = DimensionSpriteUtil.CreateWorldIcon(rawPosition.getWorldDimension());
        add(icon, 4, 0, 1, 1);

        WTextField name = CreateNameField();
        add(name, 2, 2, 5, 1);

        WWidget save = CreateSaveButton(coordinateSaveHandler, rawPosition, name);
        add(save, 5, 5, 2, 1);

        WWidget list = CreateListButton();
        add(list, 0, 5, 2, 1);
    }

    private void initializeListView(List<PlayerPosition> playerPositions) {
        BiConsumer<PlayerPosition, CoordinateListItem> configurator = (PlayerPosition position,
                CoordinateListItem panel) -> {
            panel.setPosition(position);
        };

        WListPanel<PlayerPosition, CoordinateListItem> listPanel = new WListPanel<>(playerPositions,
                CoordinateListItem::new, configurator);

        listPanel.setListItemHeight(2 * 18);

        add(listPanel, 0, 0, 7, 7);

    }

    private void add(WWidget widget, int x, int y, int w, int h) {
        root.add(widget, x, y, w, h);
        children.add(widget);
    }

    private void reset() {
        children.forEach(root::remove);
        children.clear();
    }

    private WGridPanel createRootPanel() {
        WGridPanel panel = new WGridPanel(18);
        panel.setSize(7 * 18, 7 * 18);

        setRootPanel(panel);
        return panel;
    }

    private WWidget CreateWidgetForCoordinate(long l) {
        return new WText(new LiteralText(String.valueOf(l)), 0x3939ac);
    }

    private WTextField CreateNameField() {
        return new WTextField(new LiteralText("name"));
    }

    private WWidget CreateSaveButton(ICoordinateSaveHandler onSaveHandler, PlayerRawPosition rawPosition,
            WTextField textField) {
        WButton button = new WButton(new LiteralText("save"));
        button.setOnClick(new Runnable() {
            @Override
            public void run() {
                onSaveHandler.save(new PlayerPosition(rawPosition, textField.getText()));
                reset();
                screen.close();
            }
        });
        return button;
    }

    private WWidget CreateListButton() {

        CoordinatesGui gui = this;
        WButton button = new WButton(new LiteralText("list"));

        button.setOnClick(new Runnable() {

            @Override
            public void run() {
                reset();
                root.validate(gui);

                List<PlayerPosition> playerPositions = coordinateListHandler.list();
                initializeListView(playerPositions);

                root.validate(gui);
            }
        });

        return button;
    }
}
