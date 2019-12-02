package com.kator.weightguard.ui.server.request;

import javafx.concurrent.Task;

/**
 * Created by prats on 4/24/18.
 */
public class RequestTask extends Task<Void> {
    private Runnable runnable;

    public RequestTask(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    protected Void call() throws Exception {
        runnable.run();
        return null;
    }
}
