package com.nobbyknox.cibecs.commons.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Comms2Tests {

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
                Thread.sleep(2000);
                Comms.startClient("", 9050);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        };

        new Thread(clientRunner).start();
    }

    @Test
    void test001() throws Exception {
        int shortSleepTime = 1500;
        int longSleepTime = 3000;

        Thread.sleep(longSleepTime);

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

        Comms.tellClient("Yes");
        Thread.sleep(shortSleepTime);

        Comms.tellClient("I mean no");
        Thread.sleep(shortSleepTime);
    }

}
