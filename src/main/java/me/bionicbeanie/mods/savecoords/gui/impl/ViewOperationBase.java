package me.bionicbeanie.mods.savecoords.gui.impl;

import java.util.function.Supplier;

import me.bionicbeanie.mods.savecoords.IFileStore;

abstract class ViewOperationBase<T> implements Runnable {

    private IFileStore fileStore;
    private Supplier<T> stateSupplier;

    public ViewOperationBase(IFileStore fileStore,Supplier<T> stateSupplier) {
        this.fileStore = fileStore;
        this.stateSupplier = stateSupplier;
    }
    
    @Override
    public void run() {
        try {
            executeOperation(fileStore, stateSupplier.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void executeOperation(IFileStore fileStore, T state) throws Exception;
}
