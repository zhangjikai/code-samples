package com.zhangjikai.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by Jikai Zhang on 2017/5/13.
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        String address = "localhost";
        int port = 1234;
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(address, port));
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            //System.out.println(1111);
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handleKey(key, selector);
            }
        }
    }


    public static void handleKey(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel server = null;
        SocketChannel client = null;
        if(key.isAcceptable()) {
            System.out.println("Acceptable");
            server = (ServerSocketChannel) key.channel();
            client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } else if(key.isReadable()) {
            client = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(200);
            int count = client.read(byteBuffer);
            if(count > 0) {
                System.out.println("Readable");
                System.out.println(new String(byteBuffer.array()));
            } else if(count == -1) {
                key.cancel();
                return;
            }
        }
    }
}
