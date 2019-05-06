package com.nobbyknox.cibecs.commons.communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

/*
 * Check out:
 * - https://www.javatpoint.com/socket-programming
 */

public class TcpClient {
    private Logger logger = LogManager.getLogger();
    private String host;
    private int port;
    private ReceiveHandler<String> receiveHandler;

    private Socket s;
    private DataInputStream din;
    private DataOutputStream dout;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws Exception {
        s = new Socket(this.host, this.port);
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

/*
        String str = "", str2 = "";
        while (!str.equals("stop")) {
            logger.debug("Reading...");

            str = br.readLine();
            logger.debug("server says: " + str);

            dout.writeUTF(str);
            dout.flush();

            str2 = din.readUTF();
            logger.debug("server also says: " + str2);
        }
*/
        String str = "", str2 = "";
        while (!str.equals("stop")) {
//            logger.debug("Reading...");

            try {
                str = din.readUTF();

                logger.debug(str);

                if (this.receiveHandler != null) {
                    this.receiveHandler.handle(str);
                }
            } catch (IOException e) {
                // Force the server to close down
                str = "";
            }
//            logger.debug("server says: " + str);


//            str2 = br.readLine();
//            logger.debug("server also says: " + str2);

//            dout.writeUTF(str2);
//            dout.flush();
        }

        logger.debug("Client disconnecting");

    }

    public void stopClient() {
        // TODO: Rethink this method
//        try {
//            din.close();
//            dout.close();
//            s.close();
//        } catch (IOException e) {
//            // We're closing down, so any exception here is not of major concern.
//            // Sweep it under the carpet.
//        }
    }

    public void sendMessage(String message) throws Exception {
        dout.writeUTF(message);
        dout.flush();
    }

    public void registerHandler(ReceiveHandler<String> handler) {
        this.receiveHandler = handler;
    }


}
