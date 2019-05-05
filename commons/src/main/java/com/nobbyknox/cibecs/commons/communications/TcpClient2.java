package com.nobbyknox.cibecs.commons.communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/*
 * Check out:
 * - https://crunchify.com/java-nio-non-blocking-io-with-server-client-example-java-nio-bytebuffer-and-channels-selector-java-nio-vs-io/
 * - https://stackoverflow.com/questions/905781/how-to-read-and-write-data-and-accepting-connection-using-socket-channel
 */

// TODO: This client needs to be able to read as well
public class TcpClient2 {
    private Logger logger = LogManager.getLogger();

    private static final int BUFFER_SIZE = 1024;

    // The buffer into which we'll read data when it's available
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    private InetSocketAddress hostAndPort = new InetSocketAddress("0.0.0.0", 9050);


    private int port;
    private Selector selector;

    public TcpClient2(int port) throws IOException {
        this.port = port;
        selector = initSelector();
        loop();
    }

    private void loop() {
        while (true) {
            try{
                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    // Check what event is available and deal with it
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        read(key);
//                    } else if (key.isWritable()) {
//                        write(key);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
        socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        socketChannel.register(selector, SelectionKey.OP_READ);

        System.out.println("Client is connected");
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // Clear out our read buffer so it's ready for new data
        readBuffer.clear();

        // Attempt to read off the channel
        int numRead;

        try {
            numRead = socketChannel.read(readBuffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();

            System.out.println("Forceful shutdown");
            return;
        }

        if (numRead == -1) {
            System.out.println("Graceful shutdown");
            key.channel().close();
            key.cancel();

            return;
        }

        byte[] data = new byte[numRead];
        System.arraycopy(readBuffer.array(), 0, data, 0, numRead);
        String result = new String(data, StandardCharsets.UTF_8).trim();

        logger.debug("Got from server: " + result);

/*
        if (result.equals("exit")) {
            logger.debug("Saw exit command");
            write(key, "Good bye");
        } else if (result.equals("graph")) {
            write(key, "file001");
            write(key, "file002");
            write(key, "file003");
        } else {
            logger.debug("Got from client: " + result);
            write(key, result);
        }
*/

    }

    private void write(SelectionKey key, String message) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer dummyResponse = ByteBuffer.wrap((message + "\n").getBytes(StandardCharsets.UTF_8));

        socketChannel.write(dummyResponse);
        if (dummyResponse.remaining() > 0) {
            System.err.print("Filled UP");
        }

//        key.interestOps(SelectionKey.OP_READ);
    }

    private Selector initSelector() throws IOException {
        Selector socketSelector = SelectorProvider.provider().openSelector();

        SocketChannel serverChannel = SocketChannel.open();
        serverChannel.configureBlocking(false);

        serverChannel.bind(hostAndPort);
        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
        return socketSelector;
    }

    public void sendMessage(String message) throws Exception {

    }

}
