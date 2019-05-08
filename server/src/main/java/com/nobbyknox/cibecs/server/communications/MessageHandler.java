package com.nobbyknox.cibecs.server.communications;

import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.communications.EchoMessage;
import com.nobbyknox.cibecs.commons.communications.FileContentMessage;
import com.nobbyknox.cibecs.commons.communications.Message;
import com.nobbyknox.cibecs.commons.communications.TreeGraphMessage;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import com.nobbyknox.cibecs.server.api.ServerFilesystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageHandler {
    private static Logger logger = LogManager.getLogger();

    public static void handle(Message message) throws Exception {
        if (message == null) {
            return;
        }

        if (message instanceof EchoMessage) {
            handleEchoMessage((EchoMessage) message);
        } else if (message instanceof TreeGraphMessage) {
            handleTreeGraphMessage((TreeGraphMessage) message);
        } else if (message instanceof FileContentMessage) {
            handleFileContentMessage((FileContentMessage) message);
        }
    }

    private static void handleEchoMessage(EchoMessage message) {
        logger.info(message);
    }

    private static void handleTreeGraphMessage(TreeGraphMessage message) {
        logger.info("Received tree graph message for " + message.getAccountCode());

        message.getTreeGraph().printGraph();
        ServerFilesystem.initiateSourceSync(message);
    }

    private static void handleFileContentMessage(FileContentMessage message) {
        logger.info("Received file " + message.getPath());

        try {
            String destPath = Config.getConfigValue(ConfigName.TARGET_DIR.getName()).get() +
                File.separator +
                message.getAccountCode() +
                File.separator +
                message.getPath();

            Files.write(Paths.get(destPath), message.getContents());
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}
