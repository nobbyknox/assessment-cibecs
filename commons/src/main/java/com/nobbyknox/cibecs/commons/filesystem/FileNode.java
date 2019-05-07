package com.nobbyknox.cibecs.commons.filesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileNode extends Node implements Serializable {

    private String name;
    private String path;
    private List<Node> children;

    public FileNode(String name, String path) {
        this.name = name;
        this.path = path;
        this.children = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public List<Node> getChildren() {
        return this.children;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
