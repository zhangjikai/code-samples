package com.zhangjikai.basic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

/**
 * Created by Jikai Zhang on 2017/5/12.
 */
public class PipeTest {

    public static void main(String[] args) throws IOException {
        WritableByteChannel out = Channels.newChannel(System.out);
        ReadableByteChannel workChannel = startWorker(10);
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        while (workChannel.read(byteBuffer) >= 0) {
            byteBuffer.flip();
            out.write(byteBuffer);
            byteBuffer.clear();
        }
    }

    public static ReadableByteChannel startWorker(int reps) throws IOException {
        Pipe pipe = Pipe.open();

        Worker worker = new Worker(pipe.sink(), reps);
        worker.start();
        return pipe.source();
    }

    static class Worker extends Thread {
        WritableByteChannel channel;
        private int reps;

        Worker(WritableByteChannel channel, int reps) {
            this.channel = channel;
            this.reps = reps;
        }

        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            try {


                for (int i = 0; i < this.reps; i++) {
                    doSomeWork(buffer);
                    while (channel.write(buffer) > 0) {}
                }
                this.channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String[] products = {
                "No good deed goes unpunished",
                "To be, or what?",
                "No matter where you go, there you are",
                "Just sya \"Yo\"",
                "My karma ran over my dogma"
        };
        Random random = new Random();

        private void doSomeWork(ByteBuffer buffer) {
            int product = random.nextInt(products.length);
            buffer.clear();
            buffer.put(products[product].getBytes());
            buffer.put("\r\n".getBytes());
            buffer.flip();

        }
    }
}
