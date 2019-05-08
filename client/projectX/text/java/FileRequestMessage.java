package com.nobbyknox.cibecs.commons.communications;

import java.io.Serializable;

public class FileRequestMessage implements Message, Serializable {
//    private String accountCode;
    private String path;

    public FileRequestMessage(/* String accountCode, */ String path) {
//        this.accountCode = accountCode;
        this.path = path;
    }

//    /**
//     * The fictitious account code for one of our customers.
//     *
//     * @return unique customer account code
//     */
//    public String getAccountCode() {
//        return this.accountCode;
//    }

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
