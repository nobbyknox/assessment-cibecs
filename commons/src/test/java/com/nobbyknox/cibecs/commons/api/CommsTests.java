package com.nobbyknox.cibecs.commons.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class CommsTests {

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
    }

    @Test
    void test001() throws Exception {
        Thread.sleep(1000);

        Comms.startClient("127.0.0.1", 9050);
        Comms.sendMessage("graph");
//        Comms.stopClient();
    }

}
