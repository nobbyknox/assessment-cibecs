package com.nobbyknox.secret001.commons.communications;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP socket server that endpoint clients connect to and upload
 * files from these endpoints.
 */
public class TcpServer {
    private int port;
    private ReceiveHandler<Message> receiveHandler;

    private ServerSocket serverSocket;
    private Socket socket;

    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    /**
     * Instantiates a new TCP socket server on the provided port number
     *
     * @param port port number on which to listen
     */
    public TcpServer(int port) {
        this.port = port;
    }

    /**
     * Start the TCP socket server, accept incoming connections and enter a read loop
     *
     * @throws IOException if the server cannot be created or incoming connections
     * cannot be accepted
     */
    public void startServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        socket = this.serverSocket.accept();

        this.objOut = new ObjectOutputStream(socket.getOutputStream());
        this.objIn = new ObjectInputStream(socket.getInputStream());

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
                // Generally, we see a lot of exceptions here when clients disconnect.
                // These are swallowed to keep the user from panicking.
            }
        }
    }

    /**
     * Shut down by closing all streams, followed by the socket and server
     */
    public void stopServer() {
        try {
            this.objIn.close();
            this.objOut.close();
            this.socket.close();
            this.serverSocket.close();
        } catch (Exception exc) {
            // We're closing down, so any exception here is not of major concern.
            // Sweep it under the carpet.
        }
    }

    /**
     * Send a message of type {@link Message} to the connected client
     *
     * @param message to send to the client
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
