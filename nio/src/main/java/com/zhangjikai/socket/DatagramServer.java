package com.zhangjikai.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by Jikai Zhang on 2017/5/14.
 */
public class DatagramServer {

    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.socket().bind(new InetSocketAddress(1111));
        ByteBuffer buffer = ByteBuffer.allocate(64);

        while (true) {

            datagramChannel.receive(buffer);
            buffer.flip();

            while (buffer.hasRemaining()) {
                System.out.write(buffer.get());
            }
            System.out.println();
            buffer.clear();
        }
    }
}
