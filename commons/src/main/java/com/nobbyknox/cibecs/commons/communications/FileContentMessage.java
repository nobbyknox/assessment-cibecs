package com.nobbyknox.cibecs.commons.communications;

import java.io.Serializable;

public class FileContentMessage implements Message, Serializable {

    private String accountCode;
    private String path;
    private byte[] contents;

    public FileContentMessage(String accountCode, String path) {
        this.accountCode = accountCode;
        this.path = path;
    }

    /**
     * The fictitious account code for one of our customers.
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
