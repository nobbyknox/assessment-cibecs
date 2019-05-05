package com.nobbyknox.cibecs.commons.configuration;

import com.nobbyknox.cibecs.commons.exceptions.ConfigException;

/**
 * Configuration provider interface
 *
 * This is the contract that all configuration providers are bound.
 */
public interface ConfigProvider {

    /**
     * For a list of configuration names, check that everyone has been configured.
     *
     * This allows the system to fail early. Additionally, it also provides the
     * operator with detailed information about which config items were not
     * configured.
     *
     * @param names string array of config names to check
     * @throws ConfigException is thrown when mandatory configs are missing
     */
    void checkConfiguration(String... names) throws ConfigException;

    /**
     * Gets a string value for the given config name.
     *
     * @param name config item name
     * @return config value
     * @throws ConfigException is thrown when the config item is missing
     */
    String getConfigValue(String name) throws ConfigException;

    /**
     * Gets an int value for the given config name.
     *
     * @param name config item name
     * @return config value that has been cast to an int
     * @throws ConfigException is thrown when the config item is missing
     */
    int getIntConfigValue(String name) throws ConfigException;
}
