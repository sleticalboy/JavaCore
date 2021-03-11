package com.binlee.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2021/3/11
 */
public final class EchoServer extends Thread {

    private final ServerSocket serverSocket;

    public EchoServer(int port) throws IOException {
        // 监听客户端连接：
        // 1、java API 调用 setImpl() 创建 SocketImpl 对象，kernel 会帮我们创建一个 socket，
        //   此 socket 即可用于监听客户连接，也可连接远端服务；
        // 2、java API 调用 bind 和 listen 开始监听客户端连接；
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) {
        try {
            new EchoServer(9097).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 接收 client 数据并原封返回给 client
        try {
            // 等待客户端连接，此方法返回一个 Socket 实例用于通信
            Socket client = serverSocket.accept();
            // 开始处理客户端请求
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            // 接收客户端的数据并返回给它
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
