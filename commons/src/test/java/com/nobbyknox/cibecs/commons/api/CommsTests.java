package com.nobbyknox.cibecs.commons.api;

import com.nobbyknox.cibecs.commons.communications.EchoMessage;
import com.nobbyknox.cibecs.commons.communications.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommsTests {

    private Logger logger = LogManager.getLogger();
    private int serverMessagesReceived = 0;
    private int clientMessagesReceived = 0;

    @BeforeAll
    static void beforeAll() {

        /*
         * Suppress logging from TCP client and server.
         *
         * Normally, you would just set the log level for the entire unit test run.
         * However, I'm using these tests to develop my solution and need finer
         * control over who logs what.
         */
        Configurator.setLevel("com.nobbyknox.cibecs.commons.communications.TcpServer", Level.ERROR);
        Configurator.setLevel("com.nobbyknox.cibecs.commons.communications.TcpClient", Level.ERROR);

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
    @DisplayName("should have conversation over comms")
    void shouldHaveConversationOverComms() throws Exception {

        // Give enough time for the server and client to start/connect
        Thread.sleep(3000);

        Comms.registerServerReceiveHandler(this::serverHandler);
        Comms.registerClientReceiveHandler(this::clientHandler);

        // Strike up a conversation
        Comms.tellServer(new EchoMessage("1"));

        // Give enough time for the conversation between client and server
        // to complete. JUnit has no way to "know" when the threads have
        // completed their chats.
        Thread.sleep(1000);

        /*
         * With these simple assertions we assert a few things:
         * 1. The comms implementation works
         * 2. Both server and client received messages that:
         *   2.1 Encoded/decoded correctly
         *   2.2 Was comprehended and acted upon accordingly
         *   2.3 We can implement a rudimentary workflow
         */
        assertEquals(3, this.serverMessagesReceived);
        assertEquals(2, this.clientMessagesReceived);
    }

    private void serverHandler(Message message) throws Exception {
        logger.debug("Client says: " + message);

        if (message instanceof EchoMessage) {
            this.serverMessagesReceived++;
            EchoMessage msg = (EchoMessage) message;

            if (msg.getPayload().equals("1")) {
                Comms.tellClient(new EchoMessage("2"));
            } else if (msg.getPayload().equals("3")) {
                Comms.tellClient(new EchoMessage("4"));
            }
        }
    }

    private void clientHandler(Message message) throws Exception {
        logger.debug("Server says: " + message);

        if (message instanceof EchoMessage) {
            this.clientMessagesReceived++;
            EchoMessage msg = (EchoMessage) message;

            if (msg.getPayload().equals("2")) {
                Comms.tellServer(new EchoMessage("3"));
            } else if (msg.getPayload().equals("4")) {
                Comms.tellServer(new EchoMessage("5"));
            }
        }
    }

}
