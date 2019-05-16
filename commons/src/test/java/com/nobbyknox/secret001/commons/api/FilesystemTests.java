package com.nobbyknox.secret001.commons.api;

import com.nobbyknox.secret001.commons.filesystem.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilesystemTests {

    private Logger logger = LogManager.getLogger();

    @Test
    void shouldBuildGraph() {
        Node root = Filesystem.buildGraph("src/test/resources/dir-root");
        assertEquals(4, root.getChildren().size());

        if (logger.isDebugEnabled()) {
            root.printGraph();
        }
    }
}
