package com.nobbyknox.cibecs.commons.configuration;

import com.nobbyknox.cibecs.commons.exceptions.ConfigException;

import java.util.*;

/**
 * Provides a mock configuration provider where the config is hard-coded,
 * making for easy testing of the config API
 */
public class MockConfiguration implements ConfigProvider {

    private Map<String, String> configMap = new HashMap<>();

    /**
     * Instantiates a new mock config provider with some hard-coded values
     */
    public MockConfiguration() {
        configMap.put("string.valid", "A valid string value");
        configMap.put("string.null", null);
        configMap.put("int.valid", "1");
        configMap.put("int.null", null);
        configMap.put("int.invalid", "A invalid int value");
    }

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
            if (configMap.get(name) == null) {
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
        String value = configMap.get(name);
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
