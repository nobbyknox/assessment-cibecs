package com.nobbyknox.cibecs.commons.communications;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Check out:
 * - https://www.javatpoint.com/socket-programming
 */

public class TcpServer {
    private int port;
    private ReceiveHandler<Message> receiveHandler;

    private ServerSocket serverSocket;
    private Socket socket;

    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    public TcpServer(int port) {
        this.port = port;
    }

    public void startServer() throws Exception {
        boolean keepRunning = true;
        this.serverSocket = new ServerSocket(this.port);
        socket = this.serverSocket.accept();

        this.objOut = new ObjectOutputStream(socket.getOutputStream());
        this.objIn = new ObjectInputStream(socket.getInputStream());

        // TODO: Maybe move this to a "read" method
        while (keepRunning) {
            try {
                Object obj = objIn.readObject();

                if (obj instanceof Message) {
                    Message msg = (Message) obj;

                    if (this.receiveHandler != null) {
                        this.receiveHandler.handle(msg);
                    }
                }

            } catch (IOException exc) {
                keepRunning = false;
            }
        }
    }

    public void stopServer() {
        try {
            this.objIn.close();
            this.objOut.close();
            this.socket.close();
            this.serverSocket.close();
        } catch (IOException e) {
            // We're closing down, so any exception here is not of major concern.
            // Sweep it under the carpet.
        }
    }

    public void sendMessage(Message message) throws Exception {
        this.objOut.writeObject(message);
        this.objOut.flush();
    }

    public void registerHandler(ReceiveHandler<Message> handler) {
        this.receiveHandler = handler;
    }

}
