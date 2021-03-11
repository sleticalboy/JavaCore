package com.binlee.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2021/3/11
 */
public final class EchoClient extends Thread {

    private final Socket socket;

    public EchoClient(String host, int port) throws IOException {
        // 1、Java API 调用 setImpl() 方法，kernel 帮我们创建一个 socket；
        // 2、Java API 调用 connect() 方法，kernel 帮我们发送一个 SYN 给 server；
        // 3、server 收到 SYN 后会创建一个 socket，此 socket 用于完成三次握手的过程；
        // 4、三次握手完成之后，ServerSocket#accept() 会返回一个 socket 实例，此实例即为 #1 中 kernel 创建的 socket
        socket = new Socket(host, port);
        // 长连接，保持 TCP 连接不断开
        socket.setKeepAlive(true);
        // 若 TCP 连接断开，则 socket 不可用，从而导致读写失败；反之如果读写失败，可知 socket 不可用，进而可知 TCP 连接断开。
        // 因此：要实现一个 socket 长连接，就不断地给对方写数据并读取对方的响应，从而知道连接是否正常，这就是所谓的“心跳”。
        // 只要心跳还在，就说明 socket 活着、TCP 未断开，这就是实现长连接的思路。

        // 如果正常通信的时候，不发心跳包，避免浪费资源；
        // 如果检测到连接断开了，则关闭现有 socket 并重连
    }

    public static void main(String[] args) {
        try {
            new EchoClient("localhost", 9097).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 发送数据到 server 并接收响应
        new Thread(this::readResponse).start();
        try {
            // 通过 out 向 server 发送数据
            OutputStream out = socket.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            // 接收控制台输入的数据并发送给 server
            while ((len = System.in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readResponse() {
        try {
            // 通过 in 读取 server 响应
            InputStream in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                System.out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
