package com.nobbyknox.secret001.commons.api;

import com.nobbyknox.secret001.commons.filesystem.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Filesystem API
 *
 * <p>
 * This API provides the functionality to construct a filesystem
 * graph of the specified root path via the {@link Node} class.
 *
 */
public class Filesystem {
    private static Logger logger = LogManager.getLogger();

    /**
     * Builds a filesystem tree graph for the given path
     *
     * @param rootDir root directory of the tree graph
     * @return tree graph in the form of a {@link Node} class
     */
    public static Node buildGraph(String rootDir) {
        logger.debug(String.format("Graphing %s", rootDir));
        return getNode(new File(rootDir).toPath());
    }

    private static Node getNode(Path dir) {
        Node node = new Node(dir.toFile().getName(), dir.toFile().getPath(), true);

        try {
            Files.newDirectoryStream(dir).forEach((path) -> {
                if (path.toFile().isDirectory()) {
                    node.getChildren().add(getNode(path));
                } else {
                    node.getChildren().add(new Node(path.toFile().getName(), path.toFile().getPath(), false));
                }
            });
        } catch (IOException e) {
            // TODO: We need to surface this exception to the caller
            e.printStackTrace();
        }

        return node;
    }
}
