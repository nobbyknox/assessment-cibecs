package com.nobbyknox.cibecs.commons.api;

import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import com.nobbyknox.cibecs.commons.configuration.ConfigProvider;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Config {
    private static ConfigProvider provider = null;
    private static Logger logger = LogManager.getLogger();

    public static void configureWith(ConfigProvider provider) {
        Config.provider = provider;
    }

    /**
     * Check the configuration to ensure that A) a provider has been specified and
     * B) all mandatory config items have been configured.
     *
     * @param names list of mandatory config items
     * @throws ConfigException is thrown when no provider has been specified or when
     *                         one or more mandatory config items are missing
     */
    public static void checkConfiguration(String... names) throws ConfigException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        provider.checkConfiguration(names);
    }

    public static void printConfigHelp(Optional<String[]> requiredNames) {

        Map<String, String> helpMessages = new HashMap<>();
        helpMessages.put(ConfigName.ACCOUNT_CODE.getName(), String.format("  \"%s\" [string]: The account code of the customer", ConfigName.ACCOUNT_CODE.getName()));
        helpMessages.put(ConfigName.SYNC_DIR.getName(), String.format("  \"%s\" [string]: The root directory to transfer to the server", ConfigName.SYNC_DIR.getName()));
        helpMessages.put(ConfigName.TCP_SERVER_HOST.getName(), String.format("  \"%s\" [string]: The host name or IP address of the TCP server", ConfigName.TCP_SERVER_HOST.getName()));
        helpMessages.put(ConfigName.TCP_SERVER_PORT.getName(), String.format("  \"%s\" [integer]: The port number of the TCP server", ConfigName.TCP_SERVER_PORT.getName()));

        // Print only help for the required config variables. Don't worry the user with irrelevant config variables.
        if (requiredNames.isPresent()) {
            logger.info("The following config variables are required for the correct operation of the system:");
            Arrays.stream(requiredNames.get()).forEach((item) -> logger.info(helpMessages.get(item)));
        } else {
            logger.info("The following config variables are available:");
            helpMessages.keySet().stream().forEach((key) -> logger.info(helpMessages.get(key)));
        }
    }

    public static Optional<String> getConfigValue(String name) throws ConfigException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        return provider.getConfigValue(name);
    }

    public static Optional<Integer> getIntConfigValue(String name) throws ConfigException, NumberFormatException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        return provider.getIntConfigValue(name);
    }
}
