package com.nobbyknox.cibecs.commons.configuration;

import com.nobbyknox.cibecs.commons.exceptions.ConfigException;

import java.util.*;

public class MockConfiguration implements ConfigProvider {

    private Map<String, String> configMap = new HashMap<>();

    public MockConfiguration() {
        configMap.put("string.valid", "A valid string value");
        configMap.put("string.null", null);
        configMap.put("int.valid", "1");
        configMap.put("int.null", null);
        configMap.put("int.invalid", "A invalid int value");
    }

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

    @Override
    public Optional<String> getConfigValue(String name) {
        String value = configMap.get(name);
        return (value == null ? Optional.empty() : Optional.of(value));
    }

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
