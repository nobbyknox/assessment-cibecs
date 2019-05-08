package com.nobbyknox.cibecs.server.filesystem;

import com.nobbyknox.cibecs.commons.api.Comms;
import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.communications.FileRequestMessage;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import com.nobbyknox.cibecs.commons.filesystem.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class SyncManager {
    private Logger logger = LogManager.getLogger();

    private String accountCode;
    private String customerRoot;

    public SyncManager(String accountCode) {
        this.accountCode = accountCode;
    }

    public void initiateSourceSync(Node node) {
        try {
            this.customerRoot = Config.getConfigValue(ConfigName.TARGET_DIR.getName()).get() +
                File.separator +
                this.accountCode;

            FilesystemUtils.createDirectory(this.customerRoot);
            requestFiles(node);
        } catch (Exception e) {
            // TODO: Handle this properly
            e.printStackTrace();
        }
    }

    private void requestFiles(Node node) {
        node.getChildren().stream().forEach((child) -> {
            if (child.isDirectory()) {
                try {
                    FilesystemUtils.createDirectory(this.customerRoot + File.separator + child.getPath());
                    requestFiles(child);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                logger.debug("Requesting file " + child.getPath());

                try {
                    Comms.tellClient(new FileRequestMessage(child.getPath()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
