package com.nobbyknox.cibecs.commons.communications;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class FileTestMessage implements Message, Serializable {
    private String filename;
    private String path;
    private byte[] contents;

    public FileTestMessage(String name, String path) {
        this.filename = name;
        this.path = path;
        this.contents = path.getBytes(StandardCharsets.ISO_8859_1);
    }

    public String getFilename() {
        return filename;
    }

    public String getPath() {
        return path;
    }

    public byte[] getContents() {
        return contents;
    }

    @Override
    public String type() {
        return "FILE_TEST";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", type(), getFilename());
    }
}
