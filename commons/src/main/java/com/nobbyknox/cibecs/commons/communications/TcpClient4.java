package com.nobbyknox.cibecs.commons.communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * Check out:
 * - https://www.javatpoint.com/socket-programming
 */

// TODO: This client needs to be able to read as well
public class TcpClient4 {
    private Logger logger = LogManager.getLogger();
    private int port;

    private Socket s;
    private DataInputStream din;
    private DataOutputStream dout;

    public TcpClient4(int port) {
        this.port = port;
    }

    public void connect() throws Exception {
        s = new Socket("127.0.0.1", this.port);
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

            str = din.readUTF();
            logger.debug("server says: " + str);

//            str2 = br.readLine();
//            logger.debug("server also says: " + str2);

//            dout.writeUTF(str2);
//            dout.flush();
        }

        logger.debug("Done reading");

    }

    public void disconnect() throws Exception {
        din.close();
        dout.close();
        s.close();
    }

    public void sendMessage(String message) throws Exception {
//        logger.debug("Sending: " + message);
        dout.writeUTF(message);
        dout.flush();
    }

}
