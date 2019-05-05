package com.nobbyknox.cibecs.commons.configuration;

import com.nobbyknox.cibecs.commons.exceptions.ConfigException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentConfigProvider implements ConfigProvider {

    private Logger logger = LogManager.getLogger();

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

    @Override
    public String getConfigValue(String name) throws ConfigException {
        String value = System.getenv(name);

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
