package com.binlee.thread;

import com.binlee.util.Logger;
import com.binlee.util.NamedRunnable;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/25
 */
public final class DaemonThread {

    private static final Logger sLogger = Logger.get(DaemonThread.class);
    private Thread mThread;
    private int mCounter, mDaemon;

    private DaemonThread() {
        // 守护线程是为其他线程服务的特殊线程
        // 所有非守护线程都执行结束后，JVM 才退出
        // 守护线程不能持有需要关闭的资源
        mThread = new Thread(new NamedRunnable("DaemonThread", -1) {
            @Override
            public void run() {
                try {
                    execute(toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.setDaemon(true);
        new Thread(new NamedRunnable("Worker", 999) {
            @Override
            public void run() {
                while (mCounter <= 1000) {
                    mCounter++;
                    sLogger.v(toString() + " counter: " + mCounter);
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        sLogger.e(toString(), e);
                        break;
                    }
                }
            }
        }).start();
    }

    private void execute(String name) {
        while (mThread != null && !mThread.isInterrupted()) {
            mDaemon++;
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                sLogger.e("DaemonThread is interrupted", e);
                mThread = null;
                break;
            }
            sLogger.v(name + " is running.... " + mDaemon);
        }
        // 此处代码并不会被执行
        sLogger.v("main com.binlee.thread exit.");
    }

    public static void run() {
        new DaemonThread().run0();
    }

    private void run0() {
        if (mThread != null) {
            mThread.start();
        }
    }
}
