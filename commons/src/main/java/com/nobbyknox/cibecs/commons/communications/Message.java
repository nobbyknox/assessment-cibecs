package com.nobbyknox.cibecs.commons.communications;

public interface Message {
    /**
     * Returns the {@link MessageType} of the message
     *
     * @return the message type
     */
    MessageType getType();
}
