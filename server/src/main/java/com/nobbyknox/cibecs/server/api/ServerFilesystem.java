package com.nobbyknox.cibecs.server.api;

import com.nobbyknox.cibecs.commons.communications.TreeGraphMessage;
import com.nobbyknox.cibecs.server.filesystem.SyncManager;

/**
 * Filesystem API for the server component
 */
public class ServerFilesystem {

    /**
     * Initiates the sync operation of the client source directory with the server
     *
     * @param message tree graph message from the client with its complete
     *                filesystem graph that should be uploaded
     */
    public static void initiateSourceSync(TreeGraphMessage message) {
        new SyncManager(message.getAccountCode()).initiateSourceSync(message.getTreeGraph());
    }
}
