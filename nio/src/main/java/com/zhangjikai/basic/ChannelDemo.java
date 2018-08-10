package com.zhangjikai.basic;

import com.zhangjikai.util.Path;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by Jikai Zhang on 2017/5/8.
 */
public class ChannelDemo {

    public static void testReadFile() {
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();
        String filePath = Path.fullPath("nio.txt");
        int bufferSize = 64;
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "rw")) {
            FileChannel fileChannel = randomAccessFile.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            CharBuffer charBuffer = CharBuffer.allocate(bufferSize);
            int byteRead = fileChannel.read(byteBuffer);

            while (byteRead != -1) {
                //System.out.println("Read: " + byteRead);
                byteBuffer.flip();
                //System.out.println(getString(by));
                //System.out.println(charset.encode(byteBuffer.asCharBuffer()));


                decoder.decode(byteBuffer, charBuffer, true);
                charBuffer.flip();
                System.out.println(charBuffer);
                /*while (charBuffer.hasRemaining()) {
                    System.out.print(charBuffer.get());
                }*/

                //System.out.println(charBuffer.limit());
                //System.out.println(charBuffer);
                //System.out.println(charset.decode(buf));
                //Thread.sleep(1000);

                byteBuffer.clear();
                charBuffer.clear();
                byteRead = fileChannel.read(byteBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void testRead2() {

        try {
            Charset charset = Charset.forName("UTF-8");//Java.nio.charset.Charset处理了字符转换问题。它通过构造CharsetEncoder和CharsetDecoder将字符序列转换成字节和逆转换。
            CharsetDecoder decoder = charset.newDecoder();

            RandomAccessFile raf = new RandomAccessFile(Path.fullPath("nio.txt"), "rw");
            FileChannel fc = raf.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(64);
            CharBuffer cb = CharBuffer.allocate(64);

            int count = fc.read(buffer);
            while (count != -1) {
                System.out.println("count = " + count);
                buffer.flip();
                decoder.decode(buffer, cb, false);
                //cb.flip();
                /*while (cb.hasRemaining()) {
                    System.out.print(cb.get());
                }*/
                System.out.println();
                buffer.clear();
                cb.clear();
                count = fc.read(buffer);
            }
            raf.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        testReadFile();

    }

}
