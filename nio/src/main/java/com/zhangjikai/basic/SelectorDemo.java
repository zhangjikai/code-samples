package com.zhangjikai.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jikai Zhang on 2017/5/13.
 */
public class SelectorDemo {


    public static void main(String[] args) throws IOException {

        String host = "localhost";
        int port = 1234;
        InetSocketAddress address = new InetSocketAddress(host, port);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        System.out.println("initiating connection");
        socketChannel.connect(address);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT );

        while (true) {
            int readChannels = selector.select();
            if (readChannels == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                //System.out.println(key);
                if (key.isConnectable()) {
                    //System.out.println(1111);
                    socketChannel = (SocketChannel) key.channel();
                    socketChannel.register(selector, SelectionKey.OP_READ);


                } else if(key.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                    socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    String text = new String(byteBuffer.array()).trim();
                    System.out.println(text);
                }
                keyIterator.remove();
            }
        }

    }
}
