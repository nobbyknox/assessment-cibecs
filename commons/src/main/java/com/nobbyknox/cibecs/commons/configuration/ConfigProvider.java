package com.nobbyknox.cibecs.commons.configuration;

import com.nobbyknox.cibecs.commons.exceptions.ConfigException;

import java.util.Optional;

/**
 * Configuration provider interface
 *
 * This is the contract that binds all configuration providers.
 */
public interface ConfigProvider {

    /**
     * For a list of configuration names, check that every item has been configured
     *
     * <p>
     * This allows the system to fail early. Additionally, it also provides the
     * operator with detailed information about which config items were not
     * configured.
     *
     * @param names string array of config names to check
     * @throws ConfigException is thrown when any of the config items are missing
     */
    void checkConfiguration(String... names) throws ConfigException;

    /**
     * Gets a string value for the given config name
     *
     * @param name config item name
     * @return config value
     */
    Optional<String> getConfigValue(String name);

    /**
     * Gets an integer value for the given config name
     *
     * @param name config item name
     * @return config value that has been cast to an int
     * @throws NumberFormatException is thrown when the config value is not a valid int
     */
    Optional<Integer> getIntConfigValue(String name) throws NumberFormatException;
}
