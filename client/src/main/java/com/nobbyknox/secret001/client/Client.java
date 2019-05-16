package com.nobbyknox.secret001.client;

import com.nobbyknox.secret001.client.communications.MessageHandler;
import com.nobbyknox.secret001.commons.api.Comms;
import com.nobbyknox.secret001.commons.api.Config;
import com.nobbyknox.secret001.commons.api.Filesystem;
import com.nobbyknox.secret001.commons.communications.TreeGraphMessage;
import com.nobbyknox.secret001.commons.configuration.ConfigName;
import com.nobbyknox.secret001.commons.configuration.EnvironmentConfigProvider;
import com.nobbyknox.secret001.commons.exceptions.ConfigException;
import com.nobbyknox.secret001.commons.filesystem.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * This is the main entry point into the client component
 */
public class Client {

    private Logger logger = LogManager.getLogger();

    public static void main(String... args) {
        new Client();
    }

    /**
     * Instantiate a new client, check the configuration and connect to the server
     */
    public Client() {
        logger.info("Client starting...");

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

    /**
     * Ensure we have the mandatory config elements set. Without the following, the
     * server cannot run:
     *
     * <ul>
     * <li>Account code of the customer
     * <li>Source directory to upload to the server
     * <li>Host name or IP address of the server
     * <li>Port number of the server
     * </ul>
     *
     * @throws ConfigException
     */
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

    /**
     * Connect to the server
     *
     * @param host host name or IP address of the server
     * @param port port number of the server
     */
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

    /**
     * Initiate the file upload conversation (workflow) with the server by
     * sending it a tree graph
     */
    private void initiateFileTransfer() {
        logger.info("Initiating file transfer...");

        try {
            Node root = Filesystem.buildGraph(Config.getConfigValue(ConfigName.SOURCE_DIR.getName()).get());
            Comms.tellServer(new TreeGraphMessage(Config.getConfigValue(ConfigName.ACCOUNT_CODE.getName()).get(), root));
        } catch (Exception exc) {
            logger.error("An error occurred while sending the folder manifest to the server");
            logger.error(exc);
        }
    }
}
