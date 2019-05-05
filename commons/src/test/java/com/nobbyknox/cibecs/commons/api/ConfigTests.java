package com.nobbyknox.cibecs.commons.api;

import com.nobbyknox.cibecs.commons.configuration.MockConfiguration;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Configuration API tests
 * <p>
 * For the purpose of running these unit tests, the configuration API is backed by a
 * map implementation because of the limitation of setting environment variables from
 * the JVM. Additionally, the author wanted to keep the system as simple as possible
 * for the assessment by relieving the assessors from the burden of a complicated
 * configuration.
 * <p>
 * These tests target the Config API itself and does not concern itself too much
 * with the backing implementation, for the reasons mentioned above.
 */
class ConfigTests {

    @BeforeEach
    void setup() {
        Config.configureWith(new MockConfiguration());
    }

    @Test
    void shouldReturnValidString() throws ConfigException {
        assertEquals("A valid string value", Config.getConfigValue("string.valid"));
    }

    @Test
    void shouldFailOnNullStringValue() {
        // Parameter %s has not been configured
        String name = "string.null";
        Exception exception = assertThrows(ConfigException.class, () -> Config.getConfigValue(name));
        assertEquals(String.format("Parameter %s has not been configured", name), exception.getMessage());
    }

    @Test
    void shouldReturnValidInt() throws ConfigException {
        assertEquals(1, Config.getIntConfigValue("int.valid"));
    }

    @Test
    void shouldFailOnNoSuchConfigName() {
        String name = "no.such.name";
        Exception exception = assertThrows(ConfigException.class, () -> Config.getConfigValue(name));
        assertEquals(String.format("Parameter %s has not been configured", name), exception.getMessage());
    }

    @Test
    void shouldFailOnInvalidIntValue() {
        String name = "int.invalid";
        Exception exception = assertThrows(ConfigException.class, () -> Config.getIntConfigValue(name));
        assertEquals(String.format("Parameter %s contains an invalid integer value of %s", name, "A invalid int value"), exception.getMessage());
    }

    @Test
    void shouldFailOnCheckForMissingConfig() {
        Exception exception = assertThrows(ConfigException.class, () -> Config.checkConfiguration("missing.string", "missing.int"));
        assertEquals("The following parameters have not been configured: [missing.string, missing.int]", exception.getMessage());
    }

    @Test
    void shouldFailWithNoProvider() {
        Config.configureWith(null);
        Exception exception = assertThrows(ConfigException.class, Config::checkConfiguration);
        assertEquals("No configuration provider specified", exception.getMessage());
    }

    @Test
    void shouldFailOnValueRetrievalWithNoProvider() {
        Config.configureWith(null);
        Exception exception = assertThrows(ConfigException.class, () -> Config.getConfigValue("string.valid"));
        assertEquals("No configuration provider specified", exception.getMessage());
    }

    @Test
    void shouldFailOnIntValueRetrievalWithNoProvider() {
        Config.configureWith(null);
        Exception exception = assertThrows(ConfigException.class, () -> Config.getIntConfigValue("int.valid"));
        assertEquals("No configuration provider specified", exception.getMessage());
    }
}
