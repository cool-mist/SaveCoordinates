package me.bionicbeanie.mods.util;

import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.minecraft.util.Identifier;

public class DimensionSpriteUtil {

    public static WWidget CreateWorldIcon(String dimension) {
        return new WSprite(CreateWorldIconIdentifier(dimension));
    }

    public static Identifier CreateWorldIconIdentifier(String dimension) {
        String dimensionItem = "netherite_ingot";

        if(dimension == null) {
            dimensionItem = "barrier";
        }
        else if (dimension.contains("overworld")) {
            dimensionItem = "totem_of_undying";
        } else if (dimension.contains("end")) {
            dimensionItem = "ender_eye";
        }

        return new Identifier("minecraft:textures/item/" + dimensionItem + ".png");
    }
}
