package com.nobbyknox.cibecs.commons.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommsTests {

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
                Comms.connectClient("", 9050);
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
        int shortSleepTime = 500;

        // Give enough time for the server and client to start/connect
        Thread.sleep(3000);

        Comms.tellServer("I have these files");
        Thread.sleep(shortSleepTime);

        Comms.tellClient("Thank you for the file graph");
        Thread.sleep(shortSleepTime);

        Comms.tellServer("Want to hear a story?");
        Thread.sleep(shortSleepTime);

        Comms.tellClient("No");
        Thread.sleep(shortSleepTime);

        Comms.tellServer("You sure?");
        Thread.sleep(shortSleepTime);

        Comms.tellClient("No");
        Thread.sleep(shortSleepTime);

        Comms.tellClient("I mean yes");
        Thread.sleep(shortSleepTime);
    }

}
