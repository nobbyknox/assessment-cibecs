package com.nobbyknox.cibecs.client;

import com.nobbyknox.cibecs.client.communications.MessageHandler;
import com.nobbyknox.cibecs.commons.api.Comms;
import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

    private Logger logger = LogManager.getLogger();

    public static void main(String... args) {
        new Client();
    }

    public Client() {
        // Ensure our environment is fully configured
//        Config.checkConfiguration(ConfigName.TCP_SERVER_HOST.getName(), ConfigName.TCP_SERVER_PORT.getName());

        // Check that all the mandatory parameters/settings have been configured.
        // We cannot run without these.
        try {
            checkConfig();
        } catch (ConfigException exc) {
            // Don't log the exception, as we already printed a nice message to the user
            System.exit(1);
        }


        connectToServer(Config.getConfigValue("tcp.server.host"), Config.getIntConfigValue("tcp.server.port"));

        try {
            Thread.sleep(3000);
            Comms.registerClientReceiveHandler(MessageHandler::handle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void connectToServer(String host, int port) {
        Runnable clientRunner = () -> {
            try {
                Comms.connectClient(host, port);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        };

        new Thread(clientRunner).start();
    }
}
