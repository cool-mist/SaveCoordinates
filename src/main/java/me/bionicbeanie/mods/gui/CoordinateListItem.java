package me.bionicbeanie.mods.gui;

import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import me.bionicbeanie.mods.model.PlayerPosition;
import me.bionicbeanie.mods.util.DimensionSpriteUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class CoordinateListItem extends WPlainPanel{

    private WLabel coordinates;
    private WLabel location;
    private WSprite icon;
    
    public CoordinateListItem() {
        this.coordinates = new WLabel("Foo");
        this.location = new WLabel("Foo");
        this.icon = new WSprite(new Identifier("minecraft:textures/item/ender_eye.png"));
        
        this.add(icon, 0, 0, 1*18, 1*18);
        this.add(coordinates, 1*18, 0, 2*18, 1*18);
        this.add(location, 0, 1*18, 3*18, 1*18);
        
        this.icon.setSize(1*18, 1*18);
        this.coordinates.setSize(2*18, 1*18);
        this.location.setSize(3*18, 1*18);
        this.setSize(3*18, 2*18);
    }
    
    public void setPosition(PlayerPosition position) {
        this.icon.setImage(DimensionSpriteUtil.CreateWorldIconIdentifier(position.getWorldDimension()));
        this.location.setText(new LiteralText(position.getLocationName()));
        this.coordinates.setText(new LiteralText(position.getX() + "," + position.getY() + "," + position.getZ()));
    }
}
