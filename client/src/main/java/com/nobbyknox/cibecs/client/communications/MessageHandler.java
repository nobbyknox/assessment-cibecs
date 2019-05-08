package com.nobbyknox.cibecs.client.communications;

import com.nobbyknox.cibecs.client.api.FileUploader;
import com.nobbyknox.cibecs.commons.communications.FileRequestMessage;
import com.nobbyknox.cibecs.commons.communications.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageHandler {
    private static Logger logger = LogManager.getLogger();

    public static void handle(Message message) {
        if (message == null) {
            return;
        }

        if (message instanceof FileRequestMessage) {
            handleFileRequestMessage((FileRequestMessage) message);
        }
    }

    private static void handleFileRequestMessage(FileRequestMessage message) {
        logger.info("Request received for file " + message.getPath());
        FileUploader.handleFileRequest(message);
    }
}
