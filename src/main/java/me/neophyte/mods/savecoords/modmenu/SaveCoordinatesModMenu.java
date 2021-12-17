package me.neophyte.mods.savecoords.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.neophyte.mods.savecoords.gui.impl.DIContainer;

public class SaveCoordinatesModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return DIContainer.getModMenuScreenFactory();
    }
}
