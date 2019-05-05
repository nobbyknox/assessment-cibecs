package com.nobbyknox.cibecs.commons.api;

import com.nobbyknox.cibecs.commons.configuration.ConfigProvider;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;

public class Config {
    private static ConfigProvider provider = null;

    public static void configureWith(ConfigProvider provider) {
        Config.provider = provider;
    }


    public static void checkConfiguration(String... names) throws ConfigException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        provider.checkConfiguration(names);
    }

    public static String getConfigValue(String name) throws ConfigException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        return provider.getConfigValue(name);
    }

    public static int getIntConfigValue(String name) throws ConfigException {
        if (Config.provider == null) {
            throw new ConfigException("No configuration provider specified");
        }

        return provider.getIntConfigValue(name);
    }
}
