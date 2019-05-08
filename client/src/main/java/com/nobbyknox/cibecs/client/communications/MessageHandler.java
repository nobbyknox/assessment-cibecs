package com.nobbyknox.cibecs.client.communications;

import com.nobbyknox.cibecs.commons.api.Comms;
import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.communications.FileContentMessage;
import com.nobbyknox.cibecs.commons.communications.FileRequestMessage;
import com.nobbyknox.cibecs.commons.communications.Message;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

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

        try {
            String accountCode = Config.getConfigValue(ConfigName.ACCOUNT_CODE.getName()).get();

            FileContentMessage reply = new FileContentMessage(accountCode, message.getPath());
            reply.setContents(Files.readAllBytes(Paths.get(message.getPath())));

            Comms.tellServer(reply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
