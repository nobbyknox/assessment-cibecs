package com.nobbyknox.cibecs.commons.filesystem;

import java.util.List;

/**
 * Interface that represents a filesystem entry, which can be
 * either a directory or file.
 */
public interface FilesystemEntry {
    /**
     * Get the name of the file or directory
     *
     * @return name of the file or directory
     */
    String getName();

    /**
     * Get the path of the file or directory
     *
     * @return the path to the file or directory
     */
    String getPath();

    /**
     * Indicates whether the node represents a directory
     *
     * @return directory flag indicator
     */
    boolean isDirectory();

    /**
     * Get list of children which could be subdirectories or files
     *
     * @return list of {@link Node} items
     */
    List getChildren();
}
