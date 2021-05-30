package me.bionicbeanie.mods.savecoords.util;

import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.icon.Icon;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.minecraft.util.Identifier;

public class ResourceUtils {

    public static WWidget CreateWorldIcon(String dimension) {
        return new WSprite(CreateWorldIconIdentifier(dimension));
    }

    public static Identifier CreateWorldIconIdentifier(String dimension) {

        if (dimension == null)
            return CreateIdentifier("nonExistent");
        if (dimension.contains("overworld"))
            return CreateIdentifier("overworld");
        if (dimension.contains("nether"))
            return CreateIdentifier("nether");
        if (dimension.contains("end"))
            return CreateIdentifier("end");

        return CreateIdentifier("nonExistent");

    }

    public static Icon CreatePingIcon() {
        return new TextureIcon(CreateIdentifier("ping"));
    }

    public static Icon CreateCloseIcon() {
        return new TextureIcon(CreateIdentifier("close"));
    }

    public static Icon CreateDetailsIcon() {
        return new TextureIcon(CreateIdentifier("more"));
    }

    private static Identifier CreateIdentifier(String file) {
        return new Identifier("savecoords", "textures/gui/" + file + ".png");
    }
}
