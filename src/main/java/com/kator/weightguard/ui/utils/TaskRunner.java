package com.kator.weightguard.ui.utils;

import javafx.concurrent.Task;

/**
 * Created by prats on 4/23/18.
 */
public class TaskRunner {
    public static void run(Task<Void> task) {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
