package com.binlee.thread;

import com.binlee.util.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/9/25
 */
public final class WayToAbortThread {

    private static final Logger sLogger = Logger.get(WayToAbortThread.class);
    private final AtomicInteger mSeq;
    private volatile boolean mStarted = false;
    private Thread mThread;
    private int mPercent;

    private WayToAbortThread() {
        mSeq = new AtomicInteger(100);
    }

    public static void run() {
        final WayToAbortThread way = new WayToAbortThread();
        way.start();
        // way.stop_1();
        way.stop_2();
    }

    private void start() {
        if (mThread != null) {
            return;
        }
        mPercent = 0;
        mThread = new Thread("DownloadThread" + mSeq.getAndIncrement()) {
            @Override
            public void run() {
                while (mStarted && !interrupted()) {
                    mPercent++;
                    sLogger.v("download percent is " + mPercent + "%");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        sLogger.e(getName() + " is interrupted", e);
                        break;
                    }
                }
                sLogger.v(getName() + " exit mStarted: " + mStarted + ", interrupted: " + isInterrupted());
            }
        };
        mThread.start();
        mStarted = true;
    }

    private void stop_2() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            sLogger.e("main thread is interrupted", e);
        }
        mStarted = false;
    }

    private void stop_1() {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            sLogger.e("main thread is interrupted", e);
        }
        if (mThread != null) {
            mThread.interrupt();
        }
    }
}
