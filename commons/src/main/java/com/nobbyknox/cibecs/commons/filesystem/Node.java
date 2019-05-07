package com.nobbyknox.cibecs.commons.filesystem;

import java.io.Serializable;
import java.util.List;

public abstract class Node implements Serializable {
    abstract public String getName();
    abstract public String getPath();
    abstract public List<Node> getChildren();
    abstract public boolean isDirectory();
}
