package com.nobbyknox.cibecs.server;

import com.nobbyknox.cibecs.commons.api.Comms;
import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import com.nobbyknox.cibecs.commons.configuration.EnvironmentConfigProvider;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;
import com.nobbyknox.cibecs.server.communications.MessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * This is the main entry point into the server component
 */
public class Server {
    private Logger logger = LogManager.getLogger();

    public static void main(String... args) {
        new Server();
    }

    /**
     * Instantiate a new server, check the configuration and start a
     * socket server
     */
    public Server() {
        // Check that all the mandatory parameters/settings have been configured.
        // We cannot run without these.
        try {
            checkConfig();
        } catch (ConfigException exc) {
            // Don't log the exception, as we already printed a nice message to the user
            System.exit(1);
        }

        Runnable tcpRunner = () -> {
            try {
                Comms.startServer(Config.getIntConfigValue(ConfigName.TCP_SERVER_PORT.getName()).get());
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        };

        new Thread(tcpRunner).start();

        try {
            // Allow the server some time to start before we register the message handler
            Thread.sleep(3000);

            // This is our custom message handler that will handle
            // communications from the client.
            Comms.registerServerReceiveHandler(MessageHandler::handle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Register a shutdown hook so that we can exit cleanly
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
            Comms.stopServer();
        }));

        logger.info("Server is ready");
    }

    /**
     * Ensure we have the mandatory config elements set. Without the following, the
     * server cannot run:
     *
     * <ul>
     * <li>Target directory where client data will be saved
     * <li>Port number on which to listen
     * </ul>
     *
     * @throws ConfigException
     */
    private void checkConfig() throws ConfigException {
        String[] requiredConfigNames = {ConfigName.TARGET_DIR.getName(), ConfigName.TCP_SERVER_PORT.getName()};

        try {
            Config.configureWith(new EnvironmentConfigProvider());
            Config.checkConfiguration(requiredConfigNames);
        } catch (ConfigException exc) {
            Config.printConfigHelp(Optional.of(requiredConfigNames));
            throw exc;
        }
    }
}
