package com.nobbyknox.cibecs.commons.communications;

import java.io.Serializable;

public class EchoMessage implements Message, Serializable {
    private String payload;

    public EchoMessage(String message) {
        this.payload = message;
    }

    public String getPayload() {
        return this.payload;
    }

    @Override
    public String toString() {
        return this.payload;
    }
}
