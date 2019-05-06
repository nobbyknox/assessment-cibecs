package com.nobbyknox.cibecs.commons.filesystem;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileNode extends Node {

    private String name;
    private Path path;
    private List<Node> children;

    public FileNode(String name, Path path) {
        this.name = name;
        this.path = path;
        this.children = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Path getPath() {
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
