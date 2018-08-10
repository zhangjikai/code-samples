package com.zhangjikai.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Jikai Zhang on 2017/5/12.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 1234;
        InetSocketAddress address = new InetSocketAddress(host, port);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        System.out.println("initiating connection");
        socketChannel.connect(address);
        while (!socketChannel.finishConnect()) {
            System.out.println("do other things...");
        }
        ByteBuffer buffer = ByteBuffer.allocate(100);
        int readLen;
        while ((readLen = socketChannel.read(buffer)) != -1) {
            System.out.println(readLen);
            if(readLen != 0) {
                String result = new String(buffer.array()).trim();
                System.out.println(result);
            }
        }

        System.out.println("connection established");
        socketChannel.close();
    }
}
