package com.nobbyknox.cibecs.commons.api;

import com.nobbyknox.cibecs.commons.communications.TcpClient4;
import com.nobbyknox.cibecs.commons.communications.TcpServer4;

public class Comms {
    private static TcpServer4 server = null;
    private static TcpClient4 client = null;

    public static void startServer(int port) throws Exception {
        Comms.server = new TcpServer4(port);
        Comms.server.startServer();
    }

    public static void stopServer() {

    }

    // TODO: Maybe rename method to "connectClient"
    public static void startClient(String host, int port) throws Exception {
        Comms.client = new TcpClient4(port);
        Comms.client.connect();
    }

    public static void stopClient() throws Exception {
//        Comms.client.stopClient();
    }

    @Deprecated
    public static void sendMessage(String message) throws Exception {
        Comms.client.sendMessage(message);
    }

    public static void tellServer(String message) throws Exception {
        Comms.client.sendMessage(message);
    }

    public static void tellClient(String message) throws Exception {
        Comms.server.sendMessage(message);
    }
}
