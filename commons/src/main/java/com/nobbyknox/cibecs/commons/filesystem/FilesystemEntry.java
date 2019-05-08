package com.nobbyknox.cibecs.commons.filesystem;

import java.util.List;

public interface FilesystemEntry {
    String getName();
    String getPath();
    boolean isDirectory();
    List getChildren();
}
