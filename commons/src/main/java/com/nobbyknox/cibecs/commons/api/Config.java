package com.nobbyknox.cibecs.commons.api;

import com.nobbyknox.cibecs.commons.configuration.ConfigProvider;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;

import java.util.Optional;

public class Config {
    private static ConfigProvider provider = null;

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
