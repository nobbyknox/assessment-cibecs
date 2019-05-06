package com.nobbyknox.cibecs.commons.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommsTests {

    private Logger logger = LogManager.getLogger();
    private String expectedReply = "Greetings";
    private boolean showLogs = false;

    @BeforeAll
    static void beforeAll() {

        // Start the TCP server
        Runnable serverRunner = () -> {
            try {
                Comms.startServer(9050);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        };

        new Thread(serverRunner).start();

        // Connect the TCP client to the server
        Runnable clientRunner = () -> {
            try {
                // Allow the server enough time to start
                Thread.sleep(2000);
                Comms.connectClient("127.0.0.1", 9050);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        };

        new Thread(clientRunner).start();
    }

    @AfterAll
    static void afterAll() {
        Comms.stopClient();
        Comms.stopServer();
    }

    @Test
    void test001() throws Exception {

        // Give enough time for the server and client to start/connect
        Thread.sleep(3000);

        Comms.registerServerReceiveHandler(this::serverHandler);
        Comms.registerClientReceiveHandler(this::clientHandler);

        // Starts a conversation. The chat is directed by the respective
        // server and client handlers below.
        Comms.tellServer(this.expectedReply);
    }

    private void serverHandler(String message) throws Exception {
        if (showLogs) logger.debug("Client says: " + message);

        assertEquals(expectedReply, message);

        if (message.equalsIgnoreCase("Greetings")) {
            expectedReply = "You well?";
            Comms.tellClient("Well, hello there");
        } else if (message.equalsIgnoreCase("You well?")) {
            expectedReply = "Good, carry on";
            Comms.tellClient("Very well");
        } else if (message.equalsIgnoreCase("Good, carry on")) {
            Comms.tellClient("Good bye");
        }
    }

    private void clientHandler(String message) throws Exception {
        if (showLogs) logger.debug("Server says: " + message);

        if (message.equalsIgnoreCase("Well, hello there")) {
            Comms.tellServer("You well?");
        } else if (message.equalsIgnoreCase("Very well")) {
            Comms.tellServer("Good, carry on");
        } else if (message.equalsIgnoreCase("Good Bye")) {
            if (showLogs) logger.debug("Server left");
        }
    }

}
