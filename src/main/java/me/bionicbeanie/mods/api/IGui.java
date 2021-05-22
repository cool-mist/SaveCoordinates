package me.bionicbeanie.mods.api;

public interface IGui {

    public void init(IGuiHandler saveHandler, IGuiHandler listHandler, IScreenController screenController);
    
    public void showDefaultView();
    
    public void showListView();
    
    public void close();
    
}
