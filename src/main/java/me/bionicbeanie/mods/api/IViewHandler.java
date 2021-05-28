package me.bionicbeanie.mods.api;

public interface IViewHandler<T> {

    void setState(T state);
    void clearState();
    void placeWidgets(IRootGridPanel rootPanel);

}
