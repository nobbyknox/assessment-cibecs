package com.nobbyknox.secret001.server.communications;

import com.nobbyknox.secret001.commons.api.Config;
import com.nobbyknox.secret001.commons.communications.EchoMessage;
import com.nobbyknox.secret001.commons.communications.FileContentMessage;
import com.nobbyknox.secret001.commons.communications.Message;
import com.nobbyknox.secret001.commons.communications.TreeGraphMessage;
import com.nobbyknox.secret001.commons.configuration.ConfigName;
import com.nobbyknox.secret001.server.api.ServerFilesystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Comms message handler where all communication from the client is handled
 */
public class MessageHandler {
    private static Logger logger = LogManager.getLogger();

    /**
     * Handle an incoming message of type {@link Message}
     *
     * @param message incoming message
     */
    public static void handle(Message message) {
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

    /**
     * Handle incoming echo messages
     *
     * @param message message to echo
     */
    private static void handleEchoMessage(EchoMessage message) {
        logger.info(message);
        // TODO: Echo the message. Remember that only the server
        // can echo messages, lest we find ourselves in a circular
        // conversation.
    }

    /**
     * Handle incoming tree graph message
     *
     * @param message to process
     */
    private static void handleTreeGraphMessage(TreeGraphMessage message) {
        logger.info("Received tree graph message for " + message.getAccountCode());

        // Print the graph. It's nice to see, but this should ideally not
        // see the light of production.
        message.getTreeGraph().printGraph();

        // The client told us what it has, now we need to request all
        // the files, one by one.
        ServerFilesystem.initiateSourceSync(message);
    }

    /**
     * Handle incoming file content message
     *
     * @param message with file contents that we need to save to target directory
     */
    private static void handleFileContentMessage(FileContentMessage message) {
        logger.info("Received file " + message.getPath());
        String destPath = null;

        try {
            destPath = Config.getConfigValue(ConfigName.TARGET_DIR.getName()).get() +
                File.separator +
                message.getAccountCode() +
                File.separator +
                message.getPath();

            Files.write(Paths.get(destPath), message.getContents());
        } catch (Exception exc) {
            // What we should do is to see if we can retry the failed attempt.
            // For the time being, we will only indicate in the log file that
            // something went wrong.
            logger.error(String.format("Unable to write file \"%s\" of account \"%s\". Error:", destPath, message.getAccountCode()));
            logger.error(exc);
        }
    }

}
