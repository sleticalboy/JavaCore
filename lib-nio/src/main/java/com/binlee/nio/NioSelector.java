package com.binlee.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/12/8
 */
public final class NioSelector {

    public static void main(String[] args) {
        try {
            new NioSelector().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        // socketChannel();

        // serverSocketChannel();

        datagramChannel();
    }

    private void datagramChannel() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(4004));
        ByteBuffer buffer = ByteBuffer.allocate(128);
        // 读取数据
        channel.read(buffer);
        // do something with buffer

        buffer.clear();
        buffer.put("Hello world\r\n".getBytes(StandardCharsets.UTF_8));
        // 发送数据
        channel.send(buffer, new InetSocketAddress("localhost", 8088));

        // 连接到指定地址
        channel.connect(new InetSocketAddress("localhost", 4005));
        // 读数据
        channel.read(buffer);
        // 写数据
        channel.write(buffer);

        channel.close();
    }

    private void serverSocketChannel() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(4003));
        // 非阻塞模式
        channel.configureBlocking(false);
        while (true) {
            SocketChannel sc = channel.accept();
            if (sc != null) {
                // do stuff and continue
                continue;
            }
            break;
        }
        channel.close();
    }

    public void socketChannel() throws IOException {
        SocketChannel channel = SocketChannel.open();
        // 设置为非阻塞模式
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 4001));
        Selector selector = Selector.open();
        int ops = SelectionKey.OP_READ | SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE;
        channel.register(selector, ops, ByteBuffer.allocate(128));
        while (!channel.finishConnect()) {
            System.out.println("infinite loop...");
            if (selector.select() == 0) {
                continue;
            }
            final Set<SelectionKey> keys = selector.selectedKeys();
            System.out.println("keys: " + keys);
            for (SelectionKey key : keys.toArray(new SelectionKey[0])) {
                ByteBuffer buffer = (ByteBuffer) key.attachment();
                SocketChannel sc = (SocketChannel) key.channel();
                if (key.isAcceptable()) {
                    System.out.println("acceptable...");
                } else if (key.isConnectable()) {
                    System.out.println("connectable...");
                } else if (key.isReadable()) {
                    System.out.println("readable...");
                    sc.read(buffer);
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        System.out.print(((char) buffer.get()));
                    }
                    buffer.clear();
                } else if (key.isWritable()) {
                    System.out.println("writable...");
                    sc.write(buffer);
                }
                keys.remove(key);
            }
            break;
        }
        selector.close();
        channel.close();
    }
}
