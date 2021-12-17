package me.neophyte.mods.savecoords.gui.impl;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import me.neophyte.mods.savecoords.IFileStore;

abstract class ViewOperationBase<T> implements Callable<ErrorResponse> {

    private IFileStore fileStore;
    private Supplier<T> stateSupplier;

    public ViewOperationBase(IFileStore fileStore,Supplier<T> stateSupplier) {
        this.fileStore = fileStore;
        this.stateSupplier = stateSupplier;
    }
    
    @Override
    public ErrorResponse call() {
        try {
            executeOperation(fileStore, stateSupplier.get());
            return ErrorResponse.CreateSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorResponse.CreateFailure(e.getMessage());
        }
    }

    protected abstract void executeOperation(IFileStore fileStore, T state) throws Exception;
}

class ErrorResponse {
    
    private static ErrorResponse SUCCESS = new ErrorResponse(false);
    
    private String code;
    private boolean failed;
    
    public static ErrorResponse CreateSuccess() {
        return SUCCESS;
    }
    
    public static ErrorResponse CreateFailure(String code) {
        return new ErrorResponse(true, code);
    }
    
    private ErrorResponse(boolean failed) {
        this(failed, null);
    }
    
    private ErrorResponse(boolean failed, String code) {
        this.failed = failed;
        this.code = code;
    }
    
    public String getCode(){
        return code;
    }
    
    public boolean isFailed() {
        return failed;
    }
}
