package com.nobbyknox.secret001.commons.api;

import com.nobbyknox.secret001.commons.communications.Message;
import com.nobbyknox.secret001.commons.communications.ReceiveHandler;
import com.nobbyknox.secret001.commons.communications.TcpClient;
import com.nobbyknox.secret001.commons.communications.TcpServer;

/**
 * Communications API via TCP sockets
 */
public class Comms {
    private static TcpServer server = null;
    private static TcpClient client = null;

    /**
     * Start a socket server on the specified port
     *
     * @param port server socket port number
     * @throws Exception if any type of error occurs
     */
    public static void startServer(int port) throws Exception {
        Comms.server = new TcpServer(port);
        Comms.server.startServer();
    }

    /**
     * Register a handler to handle all messages received by the server
     *
     * @param handler lambda function that will be executed for all received messages
     */
    public static void registerServerReceiveHandler(ReceiveHandler<Message> handler) {
        Comms.server.registerHandler(handler);
    }

    /**
     * Stop the TCP socket server gracefully
     */
    public static void stopServer() {
        Comms.server.stopServer();
    }

    /**
     * Create a new TCP socket and connect to the server
     *
     * @param host host name or IP address of the server
     * @param port port number of the server
     * @throws Exception if anything goes wrong during the connection to the server
     */
    public static void connectClient(String host, int port) throws Exception {
        Comms.client = new TcpClient(host, port);
        Comms.client.connect();
    }

    /**
     * Register a handler to handle all messages received by the client
     *
     * @param handler lambda function that will be executed for all received messages
     */
    public static void registerClientReceiveHandler(ReceiveHandler<Message> handler) {
        Comms.client.registerHandler(handler);
    }

    /**
     * Stop the TCP socket gracefully
     */
    public static void stopClient() {
        Comms.client.stopClient();
    }

    /**
     * Send a message to the server
     *
     * @param message any serializable object that implements {@link Message}
     * @throws Exception if a communications error occurs
     */
    public static void tellServer(Message message) throws Exception {
        Comms.client.sendMessage(message);
    }

    /**
     * Send a message to the client
     *
     * @param message any serializable object that implements {@link Message}
     * @throws Exception if a communications error occurs
     */
    public static void tellClient(Message message) throws Exception {
        Comms.server.sendMessage(message);
    }
}
