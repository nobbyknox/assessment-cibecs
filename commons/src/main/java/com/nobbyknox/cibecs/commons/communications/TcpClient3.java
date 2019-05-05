package com.nobbyknox.cibecs.commons.communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/*
 * Check out:
 * - https://crunchify.com/java-nio-non-blocking-io-with-server-client-example-java-nio-bytebuffer-and-channels-selector-java-nio-vs-io/
 * - https://stackoverflow.com/questions/905781/how-to-read-and-write-data-and-accepting-connection-using-socket-channel
 */

// TODO: This client needs to be able to read as well
public class TcpClient3 {
    private Logger logger = LogManager.getLogger();

    private InetSocketAddress address;
    private SocketChannel client;

    public TcpClient3() {

    }

    public void connect(String host, int port) throws Exception {
        this.address = new InetSocketAddress(host, port);
        this.client = SocketChannel.open(address);
    }

    public void stopClient() throws Exception {
        client.close();
    }

    public void sendMessage(String message) throws Exception {
        byte[] byteMessage = message.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteMessage);
        client.write(buffer);

        logger.debug("sending: " + message);
        buffer.clear();

        // read
        buffer = ByteBuffer.allocate(1024);
        int numRead = client.read(buffer);

        while (numRead > 0) {
            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0, data, 0, numRead);
            String result = new String(data, StandardCharsets.UTF_8).trim();
            logger.debug("Response from server: " + result);

            numRead = client.read(buffer);
        }
/*
        int numRead = client.read(buffer);
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        String result = new String(data, StandardCharsets.UTF_8).trim();
*/
    }

    public void listen() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
//            byte[] byteMessage = message.getBytes();
//            ByteBuffer buffer = ByteBuffer.wrap(byteMessage);
//            client.write(buffer);
//
//            logger.debug("sending: " + message);
//            buffer.clear();

            // read
            logger.debug("Reading...");

            int numRead = client.read(buffer);

            while (numRead > 0) {
                byte[] data = new byte[numRead];
                System.arraycopy(buffer.array(), 0, data, 0, numRead);
                String result = new String(data, StandardCharsets.UTF_8).trim();
                logger.debug("Response from server: " + result);

                numRead = client.read(buffer);
            }

            buffer.clear();
        }
    }

}
