package com.nobbyknox.cibecs.client;

import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.api.Filesystem;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import com.nobbyknox.cibecs.commons.exceptions.ConfigException;
import com.nobbyknox.cibecs.commons.filesystem.Node;

import java.nio.file.Path;

public class FileUtils {

    public static Node buildTreeGraph() throws ConfigException {
        return Filesystem.buildGraph(Config.getConfigValue(ConfigName.SYNC_DIR.getName()).get());
    }

    public static void getFileContents(Path path) {
    }
}
