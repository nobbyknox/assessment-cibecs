package com.nobbyknox.cibecs.commons.communications;

import java.io.Serializable;

public class FileRequestMessage implements Message, Serializable {
    private String path;

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
