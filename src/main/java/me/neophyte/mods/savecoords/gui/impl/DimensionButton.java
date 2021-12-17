package me.neophyte.mods.savecoords.gui.impl;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import me.neophyte.mods.savecoords.IDimensionAware;
import me.neophyte.mods.savecoords.IDimensionAware.IDimension;
import me.neophyte.mods.savecoords.model.PlayerRawPosition;
import me.neophyte.mods.savecoords.util.PartialCircularList;
import net.minecraft.text.LiteralText;

public class DimensionButton extends WButton {

    private PartialCircularList<IDimension> allDimensions;

    public DimensionButton(WSprite sprite, IDimensionAware dimensionAware, PlayerRawPosition existingPosition) {
        allDimensions = new PartialCircularList<IDimension>();
        for (IDimension dimension : dimensionAware.getDimensions()) {
            allDimensions.add(dimension);
        }
        
        IDimension existingDimension = dimensionAware.getCurrentDimension();
        if(existingPosition != null) {
            existingDimension = dimensionAware.getDimension(existingPosition.getWorldDimension());
        }
        while(allDimensions.next() != existingDimension);
        
        IDimension currentDimension = allDimensions.current();
        
        sprite.setImage(currentDimension.getSpriteIdentifier());
        setLabel(new LiteralText(currentDimension.getName()));

        setOnClick(() -> {
            allDimensions.next();
            IDimension current = getDimension();
            sprite.setImage(current.getSpriteIdentifier());
            setLabel(new LiteralText(current.getName()));
        });
    }

    public IDimension getDimension() {
        return allDimensions.current();
    }
}