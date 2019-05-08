package com.nobbyknox.cibecs.server.filesystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesystemUtils {
    private static Logger logger = LogManager.getLogger();

    public static void createDirectory(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
        logger.info(String.format("Directory %s created", path));
    }
}
