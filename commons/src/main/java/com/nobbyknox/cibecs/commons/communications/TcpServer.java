package com.nobbyknox.cibecs.commons.communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Check out:
 * - https://www.javatpoint.com/socket-programming
 */

public class TcpServer {
    private Logger logger = LogManager.getLogger();
    private int port;
    private ReceiveHandler<String> receiveHandler;

    private ServerSocket ss;
    private Socket s;
    private DataInputStream din;
    private DataOutputStream dout;

    public TcpServer(int port) {
        this.port = port;
    }

    public void startServer() throws Exception {
        ss = new ServerSocket(this.port);
        s = ss.accept();
        din = new DataInputStream(s.getInputStream());
        dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = "", str2 = "";
        while (!str.equals("stop")) {
//            logger.debug("Reading...");

            try {
                str = din.readUTF();

                if (this.receiveHandler != null) {
                    this.receiveHandler.handle(str);
                }
            } catch (IOException e) {
                // Force the server to close down
                str = "stop";
            }
//            logger.debug("client says: " + str);


//            str2 = br.readLine();
//            logger.debug("client also says: " + str2);
//
//            dout.writeUTF(str2);
//            dout.flush();
        }
/*
        String str = "", str2 = "";
        while (!str.equals("stop")) {
            logger.debug("Reading...");

            str = br.readLine();
            logger.debug("client says: " + str);

            dout.writeUTF(str);
            dout.flush();

            str2 = din.readUTF();
            logger.debug("client also says: " + str2);
        }
*/

        logger.debug("Server shutting down");
    }

    public void stopServer() {
        // TODO: Rethink this method
//        try {
//            din.close();
//            dout.close();
//            s.close();
//            ss.close();
//        } catch (IOException e) {
//            // We're closing down, so any exception here is not of major concern.
//            // Sweep it under the carpet.
//        }
    }

    public void sendMessage(String message) throws Exception {
//        logger.debug("Sending: " + message);
        dout.writeUTF(message);
        dout.flush();
    }

    public void registerHandler(ReceiveHandler<String> handler) {
        this.receiveHandler = handler;
    }

}
