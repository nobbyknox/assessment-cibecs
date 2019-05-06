// TODO: Change package to "communications"
package com.nobbyknox.cibecs.server.communications;

import com.nobbyknox.cibecs.commons.api.Comms;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageHandler {
    private static Logger logger = LogManager.getLogger();

    public static void handle(String message) throws Exception {
        logger.debug("Server received message: " + message);

        if (message.equalsIgnoreCase("echo")) {
            Comms.tellClient(message);
        }
    }
}
