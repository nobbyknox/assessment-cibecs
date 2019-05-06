package com.nobbyknox.cibecs.commons.api;

import com.nobbyknox.cibecs.commons.filesystem.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class Filesystem {
    private static Logger logger = LogManager.getLogger();

    static Node buildGraph(String rootDir) {
        logger.debug(String.format("Graphing %s", rootDir));
        return getNode(new File(rootDir).toPath());
    }

    private static Node getNode(Path dir) {
        Node node = new DirectoryNode(dir.toFile().getName(), dir);

        try {
            Files.newDirectoryStream(dir).forEach((path) -> {
                if (path.toFile().isDirectory()) {
                    node.getChildren().add(getNode(path));
                } else {
                    node.getChildren().add(new FileNode(path.toFile().getName(), path));
                }
            });
        } catch (IOException e) {
            // TODO: We need to surface this exception to the caller
            e.printStackTrace();
        }

        return node;
    }
}
