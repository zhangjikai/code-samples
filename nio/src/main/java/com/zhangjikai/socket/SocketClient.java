package com.zhangjikai.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Jikai Zhang on 2017/5/13.
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        String address = "localhost";
        int port = 1234;
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(address, port));

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_CONNECT);

        while(true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isConnectable()) {
                    handle(key, selector);
                }
            }
        }
    }

    static void handle(SelectionKey key, Selector selector) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        if(client.isConnectionPending()) {
            if(client.finishConnect()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(200);
                byteBuffer = ByteBuffer.wrap(new String("hello server").getBytes());
                client.write(byteBuffer);
                client.register(selector, SelectionKey.OP_READ);
            }
        } else if(key.isReadable()) {

        }

    }
}
