package me.bionicbeanie.mods.api;

import me.bionicbeanie.mods.model.PlayerPosition;

public interface IGui {

    public void init(IViewHandler<PlayerPosition> saveHandler, IViewHandler<Void> listHandler,
            IScreenController screenController);

    public void showDefaultView();
    
    public void setDefaultViewState(PlayerPosition position);

    public void showListView();

    public void close();

}
