package com.nobbyknox.cibecs.commons.api;

import com.nobbyknox.cibecs.commons.communications.TcpClient;
import com.nobbyknox.cibecs.commons.communications.TcpServer;

public class Comms {
    private static TcpServer server = null;
    private static TcpClient client = null;

    public static void startServer(int port) throws Exception {
        Comms.server = new TcpServer(port);
        Comms.server.startServer();
    }

    public static void stopServer() {
        Comms.server.stopServer();
    }

    public static void connectClient(String host, int port) throws Exception {
        Comms.client = new TcpClient(port);
        Comms.client.connect();
    }

    public static void stopClient() {
        Comms.client.stopClient();
    }

    public static void tellServer(String message) throws Exception {
        Comms.client.sendMessage(message);
    }

    public static void tellClient(String message) throws Exception {
        Comms.server.sendMessage(message);
    }
}
