package com.nobbyknox.secret001.commons.configuration;

import com.nobbyknox.secret001.commons.exceptions.ConfigException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides configuration to the system from the environment. Perfect
 * for deployment through Docker containers.
 */
public class EnvironmentConfigProvider implements ConfigProvider {

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
    @Override
    public void checkConfiguration(String... names) throws ConfigException {
        // Nothing to check, so nothing to complain about :-)
        if (names == null || names.length == 0) {
            return;
        }

        List<String> missingParams = new ArrayList<>();

        for (String name : names) {
            if (System.getenv(name) == null) {
                missingParams.add(name);
            }
        }

        if (!missingParams.isEmpty()) {
            throw new ConfigException("The following parameters have not been configured: " + missingParams.toString());
        }
    }

    /**
     * Gets a string value for the given config name
     *
     * @param name config item name
     * @return config value
     */
    @Override
    public Optional<String> getConfigValue(String name) {
        String value = System.getenv(name);
        return (value == null ? Optional.empty() : Optional.of(value));
    }

    /**
     * Gets an integer value for the given config name
     *
     * @param name config item name
     * @return config value that has been cast to an int
     * @throws NumberFormatException is thrown when the config value is not a valid int
     */
    @Override
    public Optional<Integer> getIntConfigValue(String name) throws NumberFormatException {
        Optional<String> optionalStringValue = getConfigValue(name);
        Optional<Integer> returnValue = Optional.empty();

        if (optionalStringValue.isPresent()) {
            returnValue = Optional.of(Integer.parseInt(optionalStringValue.get()));
        }

        return returnValue;
    }
}
