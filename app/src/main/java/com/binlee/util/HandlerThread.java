package com.binlee.util;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/26
 */
public class HandlerThread extends Thread {

    private static final Logger sLogger = Logger.get(HandlerThread.class);
    private Looper mLooper;

    public HandlerThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Looper.loop();
    }

    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    sLogger.e("getLooper() error.", e);
                }
            }
        }
        return mLooper;
    }
}
