package com.nobbyknox.cibecs.commons.configuration;

public enum ConfigName {
    ACCOUNT_CODE("accountCode"),
    SYNC_DIR("syncDir"),
    TCP_SERVER_HOST("tcpServerHost"),
    TCP_SERVER_PORT("tcpServerPort");

    private String name;

    ConfigName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
