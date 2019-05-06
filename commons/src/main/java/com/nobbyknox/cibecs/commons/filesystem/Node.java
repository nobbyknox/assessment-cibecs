package com.nobbyknox.cibecs.commons.filesystem;

import java.nio.file.Path;
import java.util.List;

public abstract class Node {
    abstract public String getName();
    abstract public Path getPath();
    abstract public List<Node> getChildren();
    abstract public boolean isDirectory();
}
