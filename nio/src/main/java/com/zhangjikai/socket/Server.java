package com.zhangjikai.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Jikai Zhang on 2017/5/12.
 */
public class Server {

    public static final String GREETING = "Hello I must be going\r\n";

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 1234;

        ByteBuffer byteBuffer = ByteBuffer.wrap(GREETING.getBytes());
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);


        while (true) {
            System.out.println("Waiting for connections");
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel == null) {
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " + socketChannel.getRemoteAddress());
                byteBuffer.rewind();
                socketChannel.write(byteBuffer);
                socketChannel.close();
            }

        }
    }
}
