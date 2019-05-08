package com.nobbyknox.cibecs.commons.communications;

import java.io.Serializable;

/**
 * Contents of file is wrapped in this message and is sent by the client
 * to the server
 */
public class FileContentMessage implements Message, Serializable {

    private String accountCode;
    private String path;
    private byte[] contents;

    /**
     * Instantiates a new message for the requested file path for the
     * given customer
     *
     * @param accountCode unique customer account code
     * @param path of file to retrieve
     */
    public FileContentMessage(String accountCode, String path) {
        this.accountCode = accountCode;
        this.path = path;
    }

    /**
     * The fictitious account code for one of our customers
     *
     * @return unique customer account code
     */
    public String getAccountCode() {
        return this.accountCode;
    }

    /**
     * Gets the file path
     *
     * @return file path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Gets the file contents
     *
     * @return byte array of file contents
     */
    public byte[] getContents() {
        return contents;
    }

    /**
     * Sets the file contents
     *
     * @param contents byte array of file contents
     */
    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return getPath();
    }
}
