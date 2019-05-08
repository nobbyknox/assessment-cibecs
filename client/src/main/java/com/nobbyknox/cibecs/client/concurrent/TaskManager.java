package com.nobbyknox.cibecs.client.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskManager {
    // TODO: Make thread count configurable
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    public void performTask(Task task) {
        executor.execute(task);
    }
}
