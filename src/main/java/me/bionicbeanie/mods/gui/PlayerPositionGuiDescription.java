package me.bionicbeanie.mods.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import me.bionicbeanie.mods.core.ICoordinateSaveHandler;
import me.bionicbeanie.mods.model.PlayerPosition;
import me.bionicbeanie.mods.model.PlayerRawPosition;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class PlayerPositionGuiDescription extends LightweightGuiDescription {
    
    public PlayerPositionGuiDescription(PlayerRawPosition rawPosition, ICoordinateSaveHandler onSave) {
        WGridPanel root = new WGridPanel(18);
        setRootPanel(root);
        
        // 18 * 18 is the size of 1 item slot. Inset is 7 px
        // Dims should be 18 * 3 + 14 by 18 + 14
        root.setSize(70, 34);
        
        WWidget xText = CreateWidgetForCoordinate(rawPosition.getX());
        WWidget yText = CreateWidgetForCoordinate(rawPosition.getY());
        WWidget zText = CreateWidgetForCoordinate(rawPosition.getZ());
        
        root.add(xText, 0, 1, 5, 2);
        root.add(yText, 0, 2, 5, 2);
        root.add(zText, 0, 3, 5, 2);
        
        WWidget icon = CreateWorldIcon(rawPosition.getWorldDimension());
        root.add(icon, 3, 0, 1, 1);
        
        WTextField name = CreateNameField();
        root.add(name, 2, 2, 5, 5);
        
        WWidget save = CreateSaveButton(onSave, rawPosition, name);
        root.add(save, 3, 4, 2, 2);

        root.validate(this);
    }
    
    private WWidget CreateWidgetForCoordinate(long l) {
        return new WText(new LiteralText(String.valueOf(l)), 0x3939ac);
    }
    
    private WWidget CreateWorldIcon(String dimension) {
        String dimensionItem = "netherite_ingot";
        
        if(dimension.contains("overworld")) {
            dimensionItem = "diamond";
        } else if(dimension.contains("end")) {
            dimensionItem = "ender_eye";
        }
        return new WSprite(new Identifier("minecraft:textures/item/" + dimensionItem + ".png"));
    }
    
    private WTextField CreateNameField() {
        return new WTextField(new LiteralText("name"));
    }
    
    private WWidget CreateSaveButton(ICoordinateSaveHandler onSaveHandler, PlayerRawPosition rawPosition, WTextField textField) {
        WButton button = new WButton(new LiteralText("save"));
        button.setOnClick(new Runnable() {
            
            @Override
            public void run() {
                onSaveHandler.save(new PlayerPosition(rawPosition, textField.getText()));
            }
        });
        return button;
    }
}
