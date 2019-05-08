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

/**
 * Sync manager that handles the upload for an account (customer)
 */
public class SyncManager {
    private Logger logger = LogManager.getLogger();

    private String accountCode;
    private String customerRoot;

    /**
     * Instantiates a new instance for the given account
     *
     * @param accountCode unique customer code
     */
    public SyncManager(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * Initiate the file sync activity by doing two things: Firstly, create the root
     * directory. Secondly, walk the tree graph and request each file individually
     * from the client.
     *
     * @param node tree graph
     */
    public void initiateSourceSync(Node node) {
        try {
            this.customerRoot = Config.getConfigValue(ConfigName.TARGET_DIR.getName()).get() +
                File.separator +
                this.accountCode;

            FilesystemUtils.createDirectory(this.customerRoot);
            requestFiles(node);
        } catch (Exception exc) {
            logger.error(String.format("Unable to initiate source upload from account %s. Error:", this.accountCode));
            logger.error(exc);
        }
    }

    /**
     * Walk the tree graph, creating directories and requesting files from the client
     * as we go
     *
     * @param node tree graph
     */
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
                try {
                    Comms.tellClient(new FileRequestMessage(child.getPath()));
                } catch (Exception exc) {
                    // We should probably implement a retry mechanism here, but for
                    // the time being, logging the problem will suffice.
                    logger.error(String.format("Unable to ask account \"%s\" for file \"%s\". Error:", this.accountCode, child.getPath()));
                    logger.error(exc);
                }
            }
        });
    }
}
