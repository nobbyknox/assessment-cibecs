package com.nobbyknox.cibecs.client;

import com.nobbyknox.cibecs.client.communications.MessageHandler;
import com.nobbyknox.cibecs.commons.api.Comms;
import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.api.Filesystem;
import com.nobbyknox.cibecs.commons.communications.TreeGraphMessage;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import com.nobbyknox.cibecs.commons.configuration.EnvironmentConfigProvider;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;
import com.nobbyknox.cibecs.commons.filesystem.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class Client {

    private Logger logger = LogManager.getLogger();

    public static void main(String... args) {
        new Client();
    }

    public Client() {
        // Check that all the mandatory parameters/settings have been configured.
        // We cannot run without these.
        try {
            checkConfig();
        } catch (ConfigException exc) {
            // Don't log the exception, as we already printed a nice message to the user
            System.exit(1);
        }

        try {
            connectToServer(
                    Config.getConfigValue(ConfigName.TCP_SERVER_HOST.getName()).get(),
                    Config.getIntConfigValue(ConfigName.TCP_SERVER_PORT.getName()).get());

            // Allow some time for the client to connect to the server
            Thread.sleep(3000);
            Comms.registerClientReceiveHandler(MessageHandler::handle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Register a shutdown hook so that we can exit cleanly
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
            Comms.stopClient();
        }));

        logger.info("Client is ready");

        initiateFileTransfer();
    }

    private void checkConfig() throws ConfigException {
        String[] requiredConfigNames = {
                ConfigName.ACCOUNT_CODE.getName(),
                ConfigName.SOURCE_DIR.getName(),
                ConfigName.TCP_SERVER_HOST.getName(),
                ConfigName.TCP_SERVER_PORT.getName()
        };

        try {
            Config.configureWith(new EnvironmentConfigProvider());
            Config.checkConfiguration(requiredConfigNames);
        } catch (ConfigException exc) {
            Config.printConfigHelp(Optional.of(requiredConfigNames));
            throw exc;
        }
    }

    private void connectToServer(String host, int port) {
        Runnable clientRunner = () -> {
            try {
                Comms.connectClient(host, port);
            } catch (Exception e) {
                logger.error("Unable to connect to the server. Please ensure that it is running.");
                System.exit(1);
            }
        };

        new Thread(clientRunner).start();
    }

    private void initiateFileTransfer() {
        logger.info("Initiating file transfer...");

        try {
            Node root = Filesystem.buildGraph(Config.getConfigValue(ConfigName.SOURCE_DIR.getName()).get());
            Comms.tellServer(new TreeGraphMessage(Config.getConfigValue(ConfigName.ACCOUNT_CODE.getName()).get(), root));
        } catch (Exception exc) {
            logger.error("An error occurred while sending the folder manifest to the server: " + exc.getMessage());
        }
    }
}
