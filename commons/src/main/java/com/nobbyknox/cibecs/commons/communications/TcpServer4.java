package com.nobbyknox.cibecs.commons.communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Check out:
 * - https://www.javatpoint.com/socket-programming
 */

public class TcpServer4 {
    private Logger logger = LogManager.getLogger();
    private int port;

    private ServerSocket ss;
    private Socket s;
    private DataInputStream din;
    private DataOutputStream dout;

    public TcpServer4(int port) {
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

            str = din.readUTF();
            logger.debug("client says: " + str);

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

        logger.debug("Done reading");
    }

    public void stopServer() throws Exception {
        din.close();
        dout.close();
        s.close();
        ss.close();
    }

    public void sendMessage(String message) throws Exception {
//        logger.debug("Sending: " + message);
        dout.writeUTF(message);
        dout.flush();
    }

}
