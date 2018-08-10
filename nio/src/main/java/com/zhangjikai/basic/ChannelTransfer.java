package com.zhangjikai.basic;

import com.zhangjikai.util.Path;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Jikai Zhang on 2017/5/10.
 */
public class ChannelTransfer {

    public static void main(String[] args) {
        try(RandomAccessFile fromFile = new RandomAccessFile(Path.fullPath("nio.txt"), "rw");
            RandomAccessFile toFile = new RandomAccessFile(Path.fullPath("nio2.txt"), "rw")
        ) {


            FileChannel fromChannel = fromFile.getChannel();
            FileChannel toChannel = toFile.getChannel();

            /*ByteBuffer byteBuffer = ByteBuffer.allocate(64);

            int byteRead = fromChannel.read(byteBuffer);
            while (byteRead != -1) {
                System.out.println(byteRead);
                byteBuffer.flip();
                toChannel.write(byteBuffer);
                //System.out.println(toChannel.position());
                byteBuffer.clear();
                byteRead = fromChannel.read(byteBuffer);
            }
            toChannel.close();
            toFile.close();*/
            long position = 0;
            long count = fromChannel.size();
            System.out.println(count);
            toChannel.transferFrom(fromChannel, position, count);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
