package com.nobbyknox.cibecs.client;

import com.nobbyknox.cibecs.client.communications.MessageHandler;
import com.nobbyknox.cibecs.commons.api.Comms;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

    private Logger logger = LogManager.getLogger();

    public Client() {
        Runnable clientRunner = () -> {
            try {
                Comms.connectClient("127.0.0.1", 9050);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        };

        new Thread(clientRunner).start();

        try {
            Thread.sleep(3000);
            Comms.registerClientReceiveHandler(MessageHandler::handle);

            // chat a bit
            Thread.sleep(1000);
            Comms.tellServer("echo");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String... args) {
        new Client();
    }
}
