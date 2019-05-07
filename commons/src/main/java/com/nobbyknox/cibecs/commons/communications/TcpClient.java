package com.nobbyknox.cibecs.commons.communications;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*
 * Check out:
 * - https://www.javatpoint.com/socket-programming
 */

public class TcpClient {
    private String host;
    private int port;
    private ReceiveHandler<Message> receiveHandler;

    private Socket socket;

    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws Exception {
        boolean keepRunning = true;
        socket = new Socket(this.host, this.port);

        objOut = new ObjectOutputStream(socket.getOutputStream());
        objIn = new ObjectInputStream(socket.getInputStream());

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

    public void stopClient() {
        try {
            this.objIn.close();
            this.objOut.close();
            this.socket.close();
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
