package com.nobbyknox.cibecs.commons.configuration;

import com.nobbyknox.cibecs.commons.exceptions.ConfigException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockConfiguration implements ConfigProvider {

    private Map<String, String> config = new HashMap<>();

    public MockConfiguration() {
        config.put("string.valid", "A valid string value");
        config.put("string.null", null);
        config.put("int.valid", "1");
        config.put("int.null", null);
        config.put("int.invalid", "A invalid int value");
    }

    @Override
    public void checkConfiguration(String... names) throws ConfigException {
        // Nothing to check, so nothing to complain about :-)
        if (names == null || names.length == 0) {
            return;
        }

        List<String> missingParams = new ArrayList<>();

        for (String name : names) {
            if (config.get(name) == null) {
                missingParams.add(name);
            }
        }

        if (!missingParams.isEmpty()) {
            throw new ConfigException("The following parameters have not been configured: " + missingParams.toString());
        }
    }

    @Override
    public String getConfigValue(String name) throws ConfigException {
        String value = config.get(name);

        if (value == null) {
            throw new ConfigException(String.format("Parameter %s has not been configured", name));
        }

        return value;
    }

    @Override
    public int getIntConfigValue(String name) throws ConfigException {
        String stringValue = getConfigValue(name);

        if (stringValue == null || stringValue.isEmpty()) {
            throw new ConfigException(String.format("Parameter %s has not been configured", name));
        }

        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException exc) {
            throw new ConfigException(String.format("Parameter %s contains an invalid integer value of %s", name, stringValue));
        }
    }
}
