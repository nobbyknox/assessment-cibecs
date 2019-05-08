package com.nobbyknox.cibecs.client.api;

import com.nobbyknox.cibecs.client.concurrent.Task;
import com.nobbyknox.cibecs.client.concurrent.TaskManager;
import com.nobbyknox.cibecs.commons.communications.FileRequestMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUploader {
    private static Logger logger = LogManager.getLogger();
    static TaskManager taskManager;

    public static void handleFileRequest(FileRequestMessage message) {
        logger.debug("Uploading " + message.getPath());
        Task uploadTask = new Task(message);
        getTaskManager().performTask(uploadTask);
    }

    private static TaskManager getTaskManager() {
        if (taskManager == null) {
            taskManager = new TaskManager();
        }

        return taskManager;
    }
}
