package com.nobbyknox.secret001.commons.communications;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * TCP socket opened by endpoint clients that connect to the central server
 */
public class TcpClient {
    private String host;
    private int port;
    private ReceiveHandler<Message> receiveHandler;

    private Socket socket;

    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    /**
     * Instantiates a new TCP socket the the provided host and port number
     *
     * @param host host name or IP address of server
     * @param port port number of server
     */
    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Start the TCP socket server, accept incoming connections and enter a read loop
     *
     * @throws IOException if the client cannot connect to the server
     */
    public void connect() throws IOException {
        socket = new Socket(this.host, this.port);

        objOut = new ObjectOutputStream(socket.getOutputStream());
        objIn = new ObjectInputStream(socket.getInputStream());

        while (true) {
            try {
                Object obj = objIn.readObject();

                if (obj instanceof Message) {
                    Message msg = (Message) obj;

                    if (this.receiveHandler != null) {
                        this.receiveHandler.handle(msg);
                    }
                }
            } catch (Exception exc) {
                // Swallowed the exception to keep the user from panicking
            }
        }
    }

    /**
     * Shut down by closing all streams, followed by the socket
     */
    public void stopClient() {
        try {
            this.objIn.close();
            this.objOut.close();
            this.socket.close();
        } catch (Exception e) {
            // We're closing down, so any exception here is not of major concern.
            // Sweep it under the carpet.
        }
    }

    /**
     * Send a message of type {@link Message} to the server
     *
     * @param message to send to the server
     * @throws IOException if the message could not be sent
     */
    public void sendMessage(Message message) throws IOException {
        this.objOut.writeObject(message);
        this.objOut.flush();
    }

    /**
     * Register a receive handler that will handle all incoming message
     * of type {@link Message}
     *
     * @param handler lambda function to handle incoming messages
     */
    public void registerHandler(ReceiveHandler<Message> handler) {
        this.receiveHandler = handler;
    }
}
