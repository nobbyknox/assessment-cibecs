package com.nobbyknox.secret001.commons.api;

import com.nobbyknox.secret001.commons.configuration.ConfigName;
import com.nobbyknox.secret001.commons.configuration.ConfigProvider;
import com.nobbyknox.secret001.commons.exceptions.ConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Configuration API
 */
public class Config {

    private static ConfigProvider provider = null;
    private static Logger logger = LogManager.getLogger();

    /**
     * Configure the Config API with a config provider
     *
     * @param provider object that implements the {@link ConfigProvider} interface
     */
    public static void configureWith(ConfigProvider provider) {
        Config.provider = provider;
    }

    /**
     * Check the configuration to ensure that
     * A) a provider has been specified
     * B) all mandatory config items have been configured
     *
     * @param names string array of mandatory config names
     * @throws ConfigException if no provider has been specified or when one
     * or more mandatory config items are missing
     */
    public static void checkConfiguration(String... names) throws ConfigException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        provider.checkConfiguration(names);
    }

    /**
     * Print help indicating which config items are missing, or lists all in the
     * case where no names were given
     *
     * @param requiredNames optional list of mandatory config items
     */
    public static void printConfigHelp(Optional<String[]> requiredNames) {

        Map<String, String> helpMessages = new HashMap<>();
        helpMessages.put(ConfigName.ACCOUNT_CODE.getName(), String.format("  \"%s\" [string]: The account code of the customer", ConfigName.ACCOUNT_CODE.getName()));
        helpMessages.put(ConfigName.SOURCE_DIR.getName(), String.format("  \"%s\" [string]: The root directory to transfer to the server", ConfigName.SOURCE_DIR.getName()));
        helpMessages.put(ConfigName.TARGET_DIR.getName(), String.format("  \"%s\" [string]: The target directory to where files from the client will be written", ConfigName.TARGET_DIR.getName()));
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

    /**
     * Get the string value for the specified config name
     *
     * @param name configuration item name
     * @return optional config value
     * @throws ConfigException if no config provider was specified during setup
     */
    public static Optional<String> getConfigValue(String name) throws ConfigException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        return provider.getConfigValue(name);
    }

    /**
     * Get the int value for the specified config name
     *
     * @param name configuration item name
     * @return optional config value of type Integer
     * @throws ConfigException if no config provider was specified during setup
     * @throws NumberFormatException if the value is not a valid integer
     */
    public static Optional<Integer> getIntConfigValue(String name) throws ConfigException, NumberFormatException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        return provider.getIntConfigValue(name);
    }
}
