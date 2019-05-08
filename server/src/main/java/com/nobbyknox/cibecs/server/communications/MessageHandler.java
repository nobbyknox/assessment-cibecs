package com.nobbyknox.cibecs.server.communications;

import com.nobbyknox.cibecs.commons.communications.EchoMessage;
import com.nobbyknox.cibecs.commons.communications.Message;
import com.nobbyknox.cibecs.commons.communications.TreeGraphMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageHandler {
    private static Logger logger = LogManager.getLogger();

    public static void handle(Message message) throws Exception {
        logger.debug("Server received message: " + message);

        if (message == null) {
            return;
        }

        if (message instanceof EchoMessage) {
            handleEchoMessage((EchoMessage) message);
        } else if (message instanceof TreeGraphMessage) {
            handleTreeGraphMessage((TreeGraphMessage) message);
        }
    }

    private static void handleEchoMessage(EchoMessage message) {
        logger.debug(message);
    }

    private static void handleTreeGraphMessage(TreeGraphMessage message) {
        message.getTreeGraph().printGraph();
    }
}
