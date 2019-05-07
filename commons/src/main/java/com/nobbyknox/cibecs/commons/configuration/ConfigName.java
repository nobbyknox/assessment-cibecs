package com.nobbyknox.cibecs.commons.configuration;

public enum ConfigName {
    TCP_SERVER_HOST("tcp.server.host"),
    TCP_SERVER_PORT("tcp.server.port");

    private String name;

    ConfigName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
