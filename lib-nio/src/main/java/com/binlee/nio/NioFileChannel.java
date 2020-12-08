package com.binlee.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/12/8
 */
public final class NioFileChannel {

    private static final String SOURCE_DIR = "/code/github/java/JavaCore/lib-nio/src/main/resources";

    public void run() throws IOException {
        // read();
        // write();
        write2();
    }

    public void read() throws IOException {
        final String path = System.getProperty("user.home") + SOURCE_DIR + "/nio-data.txt";
        final RandomAccessFile raf = new RandomAccessFile(path, "rw");
        final FileChannel channel = raf.getChannel();
        final ByteBuffer buffer = ByteBuffer.allocate(48);
        // 1. 不停地将数据读取到缓冲区中
        while (channel.read(buffer) != -1) {
            // 2. 反转缓冲区，开始读取数据
            buffer.flip();
            // 3. 缓冲区有数据，就不停地取出 buffer.get()
            while (buffer.hasRemaining()) {
                // 注意此处不能直接用 buffer.getChar()
                System.out.print(((char) buffer.get()));
            }
            // 4. 清空 buffer，再读
            buffer.clear();
        }
        raf.close();
    }

    public void write() throws IOException {
        final String source = System.getProperty("user.home") + SOURCE_DIR + "/nio-data.txt";
        final String sink = System.getProperty("user.home") + SOURCE_DIR + "/nio-empty.txt";
        final FileChannel sourceChannel = new RandomAccessFile(source, "rw").getChannel();
        final FileChannel sinkChannel = new RandomAccessFile(sink, "rw").getChannel();
        sinkChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        sourceChannel.close();
        sinkChannel.close();
    }

    public void write2() throws IOException {
        final String source = System.getProperty("user.home") + SOURCE_DIR + "/nio-data.txt";
        final String sink = System.getProperty("user.home") + SOURCE_DIR + "/nio-empty-2.txt";
        final FileChannel sourceChannel = new RandomAccessFile(source, "rw").getChannel();
        final FileChannel sinkChannel = new RandomAccessFile(sink, "rw").getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), sinkChannel);
        sourceChannel.close();
        sinkChannel.close();
    }

    public static void main(String[] args) {
        try {
            new NioFileChannel().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
