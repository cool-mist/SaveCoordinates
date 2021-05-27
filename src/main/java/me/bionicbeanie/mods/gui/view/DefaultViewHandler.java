package me.bionicbeanie.mods.gui.view;

import java.io.IOException;
import java.util.UUID;

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

public class DefaultViewHandler extends ViewHandlerBase {

    private IPlayerLocator locator;

    public DefaultViewHandler(IFileStore fileStore, IPlayerLocator locator, MinecraftClient client, IGui gui) {
        super(fileStore, gui, client);
        this.locator = locator;
    }

    @Override
    public void placeWidgets(IRootGridPanel root) {

        PlayerRawPosition rawPosition = locator.locate(client);

        WWidget xLabel= CreateLabelForCoorindate("X");
        WWidget yLabel= CreateLabelForCoorindate("Y");
        WWidget zLabel= CreateLabelForCoorindate("Z");
        
        WWidget xText = CreateWidgetForCoordinate(rawPosition.getX());
        WWidget yText = CreateWidgetForCoordinate(rawPosition.getY());
        WWidget zText = CreateWidgetForCoordinate(rawPosition.getZ());

        root.add(xLabel, 1, 1, 2, 1);
        root.add(yLabel, 1, 2, 2, 1);
        root.add(zLabel, 1, 3, 2, 1);
        
        root.add(xText, 2, 1, 2, 1);
        root.add(yText, 2, 2, 2, 1);
        root.add(zText, 2, 3, 2, 1);

        WWidget icon = DimensionSpriteUtil.CreateWorldIcon(rawPosition.getWorldDimension());
        root.add(icon, 6, 1, 2, 2);

        WTextField name = CreateNameField();
        root.add(name, 0, 6, 8, 1);

        WWidget save = CreateSaveButton(rawPosition, name);
        root.add(save, 9, 6, 2, 1);

        WWidget list = CreateListButton();
        root.add(list, 9, 8, 2, 1);
    }

    private WWidget CreateLabelForCoorindate(String label) {
        return new WLabel(label, 0xb80000);
    }

    private WWidget CreateWidgetForCoordinate(long l) {
        return new WText(new LiteralText(String.valueOf(l)), 0x3939ac);
    }

    private WTextField CreateNameField() {
        return new WTextField(new LiteralText("location name"));
    }

    private WWidget CreateSaveButton(PlayerRawPosition rawPosition, WTextField textField) {
        WButton button = new WButton(new LiteralText("save"));
        button.setOnClick(new Runnable() {
            @Override
            public void run() {
                try {
                    String id = UUID.randomUUID().toString();
                    fileStore.save(new PlayerPosition(id, rawPosition, textField.getText(), null));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                gui.close();
            }
        });
        return button;
    }

    private WWidget CreateListButton() {
        WButton button = new WButton(new LiteralText("list"));
        button.setOnClick(gui::showListView);
        return button;
    }
}
