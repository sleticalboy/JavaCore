package com.binlee.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2021/3/11
 */
public final class KeepAliveSocket {

    private static final byte[] HEARTBEAT_PACKET = {'h', 'e', 'a', 'r', 't', 'b', 'e', 'a', 't'};
    // private static final byte[] HEARTBEAT_PACKET = "heartbeat".getBytes(StandardCharsets.UTF_8);

    private final String host;
    private final int port;
    // 心跳：发送和读取
    private final Timer sendTimer, readTimer;
    private TimerTask readTask, sendTask;
    private Socket socket;
    private int retries = 0;
    private final AtomicInteger beatSeq = new AtomicInteger(0);

    public KeepAliveSocket(String host, int port) {
        this.host = host;
        this.port = port;
        sendTimer = new Timer("Heartbeat-SendTimer");
        readTimer = new Timer("Heartbeat-ReadTimer");

        createSocket();
    }

    // idle

    // busy

    private void createSocket() {
        while (true) {
            try {
                // 创建 socket
                socket = new Socket(host, port);
                socket.setKeepAlive(true);
                // 开始心跳
                startHeartbeat();
                return;
            } catch (IOException e) {
                retries++;
                System.err.println("retry: " + retries + ", error: " + e.getMessage());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    private void startHeartbeat() throws IOException {
        if (readTask == null) readTask = new HeartbeatTask(true);
        readTimer.schedule(readTask, 500L, 1000L);

        if (sendTask == null) sendTask = new HeartbeatTask(false);
        sendTimer.schedule(sendTask, 500L, 1000L);
    }

    private void stopHeartbeat() {
        if (readTask != null) readTask.cancel();
        if (sendTask != null) sendTask.cancel();
        readTask = sendTask = null;
    }

    private void rebuildHeartbeat() {
        try {
            socket.close();
            stopHeartbeat();
            createSocket();
        } catch (IOException e) {
            System.err.println("error when rebuildHeartbeat(): " + e.getMessage());
        }
    }

    private final class HeartbeatTask extends TimerTask {

        private final boolean read;
        private final InputStream in;
        private final OutputStream out;
        private boolean canceled = false;

        private HeartbeatTask(boolean read) throws IOException {
            this.read = read;
            in = read ? socket.getInputStream() : null;
            out = read ? null : socket.getOutputStream();
        }

        @Override
        public void run() {
            if (canceled || socket == null || socket.isClosed()) return;
            // send data && read data
            try {
                if (read) {
                    if (in == null) return;
                    // 只读取前 64 字节来判断是否是心跳包
                    byte[] buffer = new byte[64];
                    int len = in.read(buffer);
                    if (len <= 0) return;
                    String result = new String(buffer, 0, len, StandardCharsets.UTF_8);
                    System.out.println("heart beat: " + result + ", seq: " + beatSeq.getAndIncrement());
                } else {
                    if (out == null) return;
                    out.write(HEARTBEAT_PACKET);
                }
            } catch (IOException e) {
                System.err.println("error when heartbeat: " + e.getMessage());
                // 发生错误，关闭 socket
                rebuildHeartbeat();
            }
        }

        @Override
        public boolean cancel() {
            return canceled = super.cancel();
        }
    }
}
