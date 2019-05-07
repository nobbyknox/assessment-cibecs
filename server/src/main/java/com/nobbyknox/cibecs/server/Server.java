package com.nobbyknox.cibecs.server;

import com.nobbyknox.cibecs.commons.api.Comms;
import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import com.nobbyknox.cibecs.commons.configuration.EnvironmentConfigProvider;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;
import com.nobbyknox.cibecs.server.communications.MessageHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Optional;

public class Server {
    private Logger logger = LogManager.getLogger();

    public static void main(String... args) {
        new Server();
    }

    public Server() {
        // Suppress the chatter of others
        Configurator.setLevel("org", Level.ERROR);

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
            Thread.sleep(3000);
            Comms.registerServerReceiveHandler(MessageHandler::handle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
            Comms.stopServer();
        }));

        logger.info("Server is ready");
    }

    private void checkConfig() throws ConfigException {
        String[] requiredConfigNames = {ConfigName.TCP_SERVER_PORT.getName()};

        try {
            Config.configureWith(new EnvironmentConfigProvider());
            Config.checkConfiguration(requiredConfigNames);
        } catch (ConfigException exc) {
            Config.printConfigHelp(Optional.of(requiredConfigNames));
            throw exc;
        }
    }
}
