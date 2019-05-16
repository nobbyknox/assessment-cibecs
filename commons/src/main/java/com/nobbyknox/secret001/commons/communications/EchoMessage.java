package com.nobbyknox.secret001.commons.communications;

import java.io.Serializable;

/**
 * Simple echo message that can be used to test the communication
 * between client ⇆ server
 */
public class EchoMessage implements Message, Serializable {
    private String payload;

    /**
     * Instantiates a new message for the specified message text
     *
     * @param message text to sent to the client or server
     */
    public EchoMessage(String message) {
        this.payload = message;
    }

    /**
     * Return the message text
     *
     * @return text message
     */
    public String getPayload() {
        return this.payload;
    }

    @Override
    public String toString() {
        return this.payload;
    }
}
