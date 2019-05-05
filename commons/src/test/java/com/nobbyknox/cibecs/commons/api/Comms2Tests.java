package com.nobbyknox.cibecs.commons.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Comms2Tests {

    @BeforeAll
    static void beforeAll() {
        Runnable serverRunner = () -> {
            try {
                Comms.startServer(9050);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        };

        new Thread(serverRunner).start();

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
        Thread.sleep(3000);

        Comms.tellServer("I have these files");
        Thread.sleep(1000);

        Comms.tellClient("Thank you for the file graph");
        Thread.sleep(1000);

        Comms.tellServer("Want to hear a story?");
        Thread.sleep(1000);

        Comms.tellClient("No");
        Thread.sleep(1000);

        Comms.tellServer("You sure?");
        Thread.sleep(1000);

        Comms.tellClient("Yes");
        Thread.sleep(1000);

        Comms.tellClient("I mean no");
        Thread.sleep(3000);
    }

}
