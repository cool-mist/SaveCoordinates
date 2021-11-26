package me.bionicbeanie.mods.savecoords.util;

import io.github.cottonmc.cotton.gui.widget.icon.Icon;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.minecraft.util.Identifier;

public class ResourceUtils {

    private static IdentifiersCache cache = new IdentifiersCache();
    
    public static Identifier getIdentifier(String resourceName) {
        return cache.get(resourceName);
    }
    
    public static Icon createPingIcon() {
        return new TextureIcon(cache.get("ping"));
    }

    public static Icon createCloseIcon() {
        return new TextureIcon(cache.get("close"));
    }

    public static Icon createDetailsIcon() {
        return new TextureIcon(cache.get("more"));
    }
    
    public static Icon createConvertIcon(boolean active) {
        String resource = "convert";
        if(active) {
            resource = "convert_active";
        }
        return new TextureIcon(cache.get(resource));
    }
}
