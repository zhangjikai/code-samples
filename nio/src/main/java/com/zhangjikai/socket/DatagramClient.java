package com.zhangjikai.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jikai Zhang on 2017/5/14.
 */
public class DatagramClient {

    static List<String> textList = new ArrayList<>(
            Arrays.asList("1111", "2222", "3333", "4444")
    );

    public static void main(String[] args) throws IOException {
        List<String> textList = new ArrayList<>(
                Arrays.asList("1111", "2222", "3333", "4444")
        );
        InetAddress hostIP = InetAddress.getLocalHost();
        InetSocketAddress address = new InetSocketAddress(hostIP, 1111);
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);

        ByteBuffer buffer = ByteBuffer.allocate(64);
        for(String text: textList) {
            System.out.println("sending msg: " + text);
            buffer.put(text.getBytes());
            buffer.flip();
            datagramChannel.send(buffer, address);
            buffer.clear();
        }
    }

}
