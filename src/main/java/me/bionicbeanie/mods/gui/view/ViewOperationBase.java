package me.bionicbeanie.mods.gui.view;

import me.bionicbeanie.mods.api.IFileStore;
import me.bionicbeanie.mods.api.IGui;

public abstract class ViewOperationBase implements Runnable {

    private IFileStore fileStore;
    private IGui gui;

    public ViewOperationBase(IFileStore fileStore, IGui gui) {
        this.fileStore = fileStore;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            executeOperation(fileStore, gui);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void executeOperation(IFileStore fileStore, IGui gui) throws Exception;
}
