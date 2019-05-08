package com.nobbyknox.cibecs.server.api;

import com.nobbyknox.cibecs.commons.communications.TreeGraphMessage;
import com.nobbyknox.cibecs.server.filesystem.SyncManager;

public class ServerFilesystem {
    public static void initiateSourceSync(TreeGraphMessage message) {
        try {
            new SyncManager(message.getAccountCode()).initiateSourceSync(message.getTreeGraph());
        } catch (Exception e) {
            // TODO: Handle this properly
            e.printStackTrace();
        }
    }
}
