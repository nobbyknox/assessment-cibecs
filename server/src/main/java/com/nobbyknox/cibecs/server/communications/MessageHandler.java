package com.nobbyknox.cibecs.server.communications;

import com.nobbyknox.cibecs.commons.communications.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageHandler {
    private static Logger logger = LogManager.getLogger();

    public static void handle(Message message) throws Exception {
        logger.debug("Server received message: " + message);
    }
}
