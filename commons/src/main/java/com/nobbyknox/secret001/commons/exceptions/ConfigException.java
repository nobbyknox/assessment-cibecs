package com.nobbyknox.secret001.commons.exceptions;

/**
 * Exception that is used to indicate a poorly or incorrectly configured
 * system
 */
public class ConfigException extends Exception {
    public ConfigException(String message) {
        super(message);
    }
}
