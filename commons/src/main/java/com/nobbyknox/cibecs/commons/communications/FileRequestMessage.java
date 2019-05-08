package com.nobbyknox.cibecs.commons.communications;

import java.io.Serializable;

/**
 * Request for a file to be sent by the client to the server
 */
public class FileRequestMessage implements Message, Serializable {
    private String path;

    /**
     * Instantiates a new message for the specified file path
     *
     * @param path file path that is requested
     */
    public FileRequestMessage(String path) {
        this.path = path;
    }

    /**
     * Returns the file path
     *
     * @return file path
     */
    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        return getPath();
    }
}
