package com.nobbyknox.cibecs.server.filesystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Filesystem utilities to keep DRY. That is to say that we will call
 * a lot and want to define only once.
 */
public class FilesystemUtils {
    private static Logger logger = LogManager.getLogger();

    /**
     * Create a directory. Internally we use {@code Files.createDirectories} for
     * two reasons. First, it does not throw an exception when the directory
     * already exists. Secondly, we can create parent directories as needed, like
     * {@code mkdir -p}
     *
     * @param path the directory to create
     * @throws IOException if the directory cannot be created
     */
    public static void createDirectory(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
        logger.info(String.format("Directory %s created", path));
    }
}
