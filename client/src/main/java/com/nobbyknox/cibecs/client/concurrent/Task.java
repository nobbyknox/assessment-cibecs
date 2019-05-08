package com.nobbyknox.cibecs.client.concurrent;

import com.nobbyknox.cibecs.commons.api.Comms;
import com.nobbyknox.cibecs.commons.api.Config;
import com.nobbyknox.cibecs.commons.communications.FileContentMessage;
import com.nobbyknox.cibecs.commons.communications.FileRequestMessage;
import com.nobbyknox.cibecs.commons.configuration.ConfigName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Task implements Runnable {
    private Logger logger = LogManager.getLogger();

    private FileRequestMessage message;

    public Task(FileRequestMessage message) {
        this.message = message;
    }

    @Override
    public void run() {
        logger.debug("Running for file " + this.message.getPath());

        try {
            String accountCode = Config.getConfigValue(ConfigName.ACCOUNT_CODE.getName()).get();

            FileContentMessage reply = new FileContentMessage(accountCode, this.message.getPath());
            reply.setContents(Files.readAllBytes(Paths.get(this.message.getPath())));

            Comms.tellServer(reply);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
